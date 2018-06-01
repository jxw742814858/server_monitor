package com.zx.kit;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
import com.zx.constant.OperateStatus;
import com.zx.entity.ServiceEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SSHCtl {
    static Logger LOG = LoggerFactory.getLogger(SSHCtl.class);
    /**
     * SSH端口
     */
    static final int SSH_PORT = 22;

    /**
     * 指令执行
     *
     * @param ser
     * @param cmds
     * @return
     */
    public static OperateStatus execute(ServiceEntity ser, String[] cmds) {
        Connection conn = new Connection(ser.getHost(), SSH_PORT);
        Session session = null;
        InputStream stdout = null;

        try {
            conn.connect();
            boolean isAuthed = conn.authenticateWithPassword(ser.getAccount(),
                    ser.getPasswd());
            if (!isAuthed)
                return OperateStatus.LOG_ERR;
        } catch (IOException e) {
            LOG.error("logging failed! msg: {}", e.getMessage());
            return OperateStatus.LOG_ERR;
        }

        try {
            session = conn.openSession();
            for (String cmd : cmds)
                session.execCommand(cmd);
        } catch (IOException e) {
            LOG.error("", e);
            return OperateStatus.OPER_ERR;
        }

        stdout = new StreamGobbler(session.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        String s;
        StringBuffer sb = new StringBuffer();

        try {
            while ((s = br.readLine()) != null)
                sb.append(s + "\n");

            if (StringUtils.isNotBlank(sb))
                return OperateStatus.NORMAL;
        } catch (IOException ioe) {
            LOG.error("", ioe);
        } finally {
            session.close();
            conn.close();
        }

        return OperateStatus.OPER_ERR;
    }

    public static String executeWithPrint(ServiceEntity ser, String[] cmds) {
        Connection conn = new Connection(ser.getHost(), SSH_PORT);
        Session session = null;
        InputStream stdout = null;
        StringBuffer buffer = new StringBuffer();

        try {
            buffer.append("----").append(ser.getHost()).append(" login...\n");
            conn.connect();
            boolean isAuthed = conn.authenticateWithPassword(ser.getAccount(), ser.getPasswd());
            if (!isAuthed) {
                buffer.append(String.format("login failed! host:[%s], account:%s, pwd:%s\n",
                        ser.getHost(), ser.getAccount(), ser.getPasswd()));
                return buffer.toString();
            }
            buffer.append("login success!\n");

            session = conn.openSession();
        } catch (IOException e) {
            buffer.append(String.format("login failed! host[%s], msg: %s\n", ser.getHost(), e.getMessage()));
            return buffer.toString();
        }

        stdout = new StreamGobbler(session.getStdout());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        String s;
        StringBuffer sb = new StringBuffer();

        try {
            for (String cmd : cmds) {
                buffer.append("  ->").append(cmd).append("\n");
                session.execCommand(cmd);
                while ((s = br.readLine()) != null)
                    sb.append(s + "\n");
                if (StringUtils.isNotBlank(sb))
                    buffer.append(sb + "\n");
            }

            buffer.append(ser.getHost()).append(" commands execute finish\n");
            buffer.append("----\n");
            return buffer.toString();
        } catch (IOException ioe) {
            LOG.error("", ioe);
        } finally {
            session.close();
            conn.close();
        }

        return null;
    }
}
