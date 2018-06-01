package com.zx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zx.constant.OperateStatus;
import com.zx.entity.MachineEntity;
import com.zx.entity.ServiceEntity;
import com.zx.kit.ParamFormat;
import com.zx.kit.SSHCtl;
import com.zx.mapper.ServiceMapper;
import com.zx.service.Service;
import com.zx.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@org.springframework.stereotype.Service("serviceImpl")
public class ServiceImpl extends ThreadPool implements Service {
    Logger log = LoggerFactory.getLogger(ServiceImpl.class);
    static Map<Integer, ServiceEntity> SERVICE_MAP = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 服务进程检查命令
     */
    static final String PROCESS_CHECK_COMMAND = "ps -fe|grep {0}|grep -v grep";

    static final String PATH_CD_COMMAND = "cd {0}";

    /**
     * 操作类型
     */
    final String STATUS = "status", START = "start", RESULT = "result", PRINT = "print";

    @Resource
    ServiceMapper mapper;

    @Override
    public void updateServiceInfo() {
        try {
//            long startTime = System.currentTimeMillis();
            log.info("before query ---- {}", sdf.format(new Date()));
            List<ServiceEntity> sers = mapper.queryServList();
            log.info("query finished ---- {}", sdf.format(new Date()));
            SERVICE_MAP.clear();
            for (ServiceEntity ser : sers)
                SERVICE_MAP.put(ser.getSid(), ser);

            sers.clear();
//            double intervalTime = new BigDecimal(Double.valueOf(System.currentTimeMillis() - startTime) / 1000)
//                    .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

//            log.info("update service info completed! used {}'s", intervalTime);
        } catch (Exception e) {
            log.info("update service info failed! msg:", e);
        }
        log.info("");
    }

    @Override
    public JSONArray querySerivces() {
        JSONArray resArr = new JSONArray();
        for (Map.Entry<Integer, ServiceEntity> entry : SERVICE_MAP.entrySet()) {
            JSONObject ij = new JSONObject();
            ij.put("host", entry.getValue().getHost());
            ij.put("service_name", entry.getValue().getService_name());
            ij.put("depict", entry.getValue().getDepict());
            resArr.add(ij);
        }
        return resArr;
    }

    @Override
    public JSONArray queryStatus() {
        JSONArray resArr = new JSONArray();
        CountDownLatch latch = new CountDownLatch(SERVICE_MAP.size());
        for (Map.Entry<Integer, ServiceEntity> entry : SERVICE_MAP.entrySet()) {
            JSONObject ijo = new JSONObject();
            ijo.put("sid", entry.getValue().getSid());
            fixedThreadPool.execute(() -> {
                String[] cmd = {MessageFormat.format(PROCESS_CHECK_COMMAND, entry.getValue().getService_name())};

                ijo.put("status", SSHCtl.execute(entry.getValue(), cmd));
                resArr.add(ijo);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        return resArr;
    }

    @Override
    public void manualOperation(JSONArray cmdArr) {

    }

    @Override
    public JSONArray autoOperation(JSONArray servStatus) {
        JSONArray operRes = new JSONArray();
        CountDownLatch latch = new CountDownLatch(servStatus.size());
        for (Object ij : servStatus) {
            JSONObject obj = (JSONObject) ij;
            if (obj.getInteger("status") < 1) {
                fixedThreadPool.execute(() -> {
                    //拼装重启使用的指令集
                    String[] cmds = {
                            MessageFormat.format(PATH_CD_COMMAND, obj.getString("path")), //cd 到程序包路径
                            obj.getString("cmad_start"), //执行启动命令
                            MessageFormat.format(PROCESS_CHECK_COMMAND, obj.getString("service_name")) //返回启动结果
                    };

                    ServiceEntity ser = SERVICE_MAP.get(obj.getInteger("sid"));
                    JSONObject ro = new JSONObject();
                    ro.put("sid", obj.getInteger("sid"));
                    ro.put("status", SSHCtl.execute(ser, cmds));
                    operRes.add(ro);
                    cmds = null;
                    latch.countDown();
                });
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        return operRes;
    }

    @Override
    public JSONObject addMachine(MachineEntity entity) {
        try {
            mapper.addMachine(entity);
            return ParamFormat.parseResult(OperateStatus.NORMAL);
        } catch (Exception e) {
            log.error("add machine error, msg: ", e);
        }

        return ParamFormat.parseResult(OperateStatus.OPER_ERR);
    }

    @Override
    public JSONObject addService(ServiceEntity entity) {
        try {
            mapper.addService(entity);
            return ParamFormat.parseResult(OperateStatus.NORMAL);
        } catch (Exception e) {
            log.error("add service error, msg: ", e);
        }

        return ParamFormat.parseResult(OperateStatus.OPER_ERR);
    }

    @Override
    public List<ServiceEntity> queryByOrderNum(int order_Num) {
        return mapper.queryServListByOrderNum(order_Num);
    }

    @Override
    public JSONArray batchQueryStatus(int[] ids) {
        return (JSONArray) batchExecute(ids, STATUS, RESULT);
    }

    @Override
    public JSONArray batchStart(int[] ids) {
        return (JSONArray) batchExecute(ids, START, RESULT);
    }

    @Override
    public String batchQueryStatusForPrint(int[] ids) {
        return (String) batchExecute(ids, STATUS, PRINT);
    }

    @Override
    public String batchStartForPrint(int[] ids) {
        return (String) batchExecute(ids, START, PRINT);
    }

    private Object batchExecute(int[] ids, String exec, String func) {
        JSONArray jarr = new JSONArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            if (i == 0)
                buffer.append(ids[i]);
            else
                buffer.append(", ").append(ids[i]);
        }

        List<ServiceEntity> sers = mapper.queryExctInfoBySids(ids);
        StringBuffer resBuffer = new StringBuffer();
        String step2Cmd = null;

        for (ServiceEntity ser : sers) {
            switch (exec) {
                case STATUS:
                    step2Cmd = ser.getCmad_status();
                    break;
                case START:
                    step2Cmd = ser.getCmad_start();
                    break;
                default:
                    break;
            }

            String[] cmds = {
                    MessageFormat.format("cd {0}", ser.getPath()),
                    step2Cmd
            };

            switch (func) {
                case RESULT:
                    JSONObject jo = new JSONObject();
                    jo.put("sid", ser.getSid());
                    jo.put("status", SSHCtl.execute(ser, cmds));
                    jarr.add(jo);
                    break;
                case PRINT:
                    resBuffer.append(SSHCtl.executeWithPrint(ser, cmds)).append("\n-------------------------");
                    break;
                default:
                    break;
            }
        }

        switch (func) {
            case RESULT:
                return jarr;
            case PRINT:
                return resBuffer;
            default:
                break;
        }

        return null;
    }
}
