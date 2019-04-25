package com.xinguan.job;

import com.xinguan.usermanage.model.BackUpData;
import com.xinguan.usermanage.repository.BackUpDataRepository;
import com.xinguan.utils.CommonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author zhangzhan
 */
@Component
@EnableScheduling
public class BackUpDataJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackUpDataJob.class);
    private BackUpDataRepository backUpDataRepository;
    private Environment environment;
    private String backUpFilePath;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    public BackUpDataJob(BackUpDataRepository backUpDataRepository, Environment environment) {
        this.backUpDataRepository = backUpDataRepository;
        this.environment = environment;
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        LOGGER.info("backUp system data job start");
        setBackUpFilePath();
        backUpSystemData();
        LOGGER.info("backUp system data job end");
    }

    /**
     * 备份系统数据
     */
    private void backUpSystemData() {
        InputStream errorStream = null;

        try {
            final StringBuilder commandBuilder = new StringBuilder();
            String backUpFilePrefixName = "_backup_data_";
            String dbName = "supervisiondb";
            String filePath = backUpFilePath + "/" + dbName + backUpFilePrefixName + new Date().getTime() + ".sql";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            commandBuilder.append("mysqldump -u ")
                    .append(databaseUsername)
                    .append(" -p")
                    .append(databasePassword)
                    .append(" ")
                    .append(dbName)
                    .append(" --result-file=")
                    .append(filePath);
            LOGGER.info("backup data command:" + commandBuilder.toString());
            Process process = Runtime.getRuntime().exec(commandBuilder.toString());
            if (process.isAlive()) {
                process.waitFor();
            }
            errorStream = process.getErrorStream();
            if (LOGGER.isDebugEnabled()) {
                String errorResult = IOUtils.toString(errorStream, StandardCharsets.UTF_8);
                if (StringUtils.isNotBlank(errorResult)) {
                    LOGGER.debug(errorResult);
                }
            }
            BackUpData backUpData = new BackUpData();
            backUpData.setDate(new Date());
            backUpData.setFilePath(filePath);
            backUpData.setBackUpType(BackUpData.BackUpType.SYSTEM_TRIGGER.toString());
            backUpData.setFileSize(CommonUtil.getNetFileSizeDescription(file.length()));
            backUpDataRepository.saveAndFlush(backUpData);
        } catch (Exception e) {
            LOGGER.error("系统数据备份失败，请确认是否配置mysqldump环境变量:" + e);
            e.printStackTrace();
        } finally {
            if (errorStream != null) {
                try {
                    errorStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据操作系统类型获取数据文件存放路径
     */
    private void setBackUpFilePath() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("win")) {
            backUpFilePath = environment.getProperty("system.data.backup.file.path.windows");
        } else {
            backUpFilePath = environment.getProperty("system.data.backup.file.path.linux");
        }
    }
}
