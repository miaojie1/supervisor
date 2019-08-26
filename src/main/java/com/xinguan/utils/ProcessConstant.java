package com.xinguan.utils;

public interface ProcessConstant {

    /**
     * 监理会议流程常量
     */
    interface Conference {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "conference";
            /**
             * 提交会议申请节点ID
             */
            static final String COMMIT_CONFERENCE = "_commit";
            /**
             * 会议纪要填写节点ID
             */
            static final String SUMMARY_FILL = "_summary";
            /**
             * 等待会议纪要汇总节点ID
             */
            static final String WAIT_SUMMARY_COLLECT = "_collect";
            /**
             * 总监审核节点ID
             */
            static final String AUDIT = "_audit";
            /**
             * 定稿节点ID
             */
            static final String FINALIZE = "_finalize";
        }

        interface NodeVariable {
            /**
             * 提交会议申请节点变量
             */
            static final String COMMIT_CONFERENCE_ASSIGNMENT = "commitUserId";
            /**
             * 会议填写节点变量
             */
            static final String SUMMARY_CANDIDATE_USER = "summaryUserIds";
            /**
             * 总监审核节点变量
             */
            static final String AUDIT_ASSIGNMENT = "userId";
            /**
             * 是否通过
             */
            static final String APPROVE_STR = "approveStr";

        }


    }

    /**
     * 进场验收流程常量
     */
    interface SiteAcceptance {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "siteAcceptanceProcess";
            /**
             * 提交进场记录ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交进场验收
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }


    /**
     * 平行检验流程常量
     */
    interface ParallelTest {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "parallelTestProcess";
            /**
             * 提交进场记录ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交平行检验
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }

    /**
     * 旁站流程常量
     */
    interface SideStation {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "sideStationProcess";
            /**
             * 提交旁站ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交旁站
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }

    /**
     * 文件审核流程常量
     */
    interface DocumentAudit{
        /**
         * 节点ID
         */
        interface NodeId{
            /**
             * 流程ID
             */
            static final String process_Key = "documentAuditProcess";

            /**
             * 上传审核文件
             */
            static final String _upload = "_upload";
            /**
             * 分配审核任务
             */
            static final String _distribution = "_distribution";
            /**
             * 审核
             */
            static final String audit = "_audit";
            /**
             * 汇总
             */
            static final String collection = "_collection";
            /**
             * 总监审核
             */
            static final String major_audit = "_majoraudit";

        }

        interface NodeVariable{
            /**
             * 上传审核文件
             */
            static final String upload_file = "userId";
            /**
             * 分配审核任务
             */
            static final String allot_audit_task = "majorUserId";
            /**
             * 总监审核
             */
            static final String major_audit = "majorUserId";
            /**
             * 审核是否通过
             */
            static final String APPROVED = "approved";
        }

    }

    /**
     * 巡视流程常量
     */
    interface Patrol {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "patrolProcess";
            /**
             * 提交巡视记录ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交巡视
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }

    /**
     * 见证取样流程常量
     */
    interface WitnessSampling {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "witnessSamplingProcess";
            /**
             * 提交见证取样记录ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交见证取样
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }

    /**
     * 检查验收流程常量
     */
    interface CheckAcceptance {
        /**
         * 节点ID
         */
        interface NodeId {
            /**
             * 流程ID
             */
            static final String PROCESS_KEY = "checkAcceptanceProcess";
            /**
             * 提交检查记录ID
             */
            static final String COMMIT_SITE_ACCEPTANCE = "_commit";
            /**
             * 相关人员审核ID
             */
            static final String AUDIT = "_audit";
            /**
             * 汇总到功能台账ID
             */
            static final String COLLECT = "_collect";
        }

        interface NodeVariable {
            /**
             * 提交检查验收
             */
            static final String COMMIT_ASSIGNMENT = "userId";
            /**
             * 是否发送相关责任人
             */
            static final String SEND_TO_CHARGE = "sendToCharge";
            /**
             * 相关人员审核
             */
            static final String AUDIT_ASSIGNMENT = "auditUserId";
        }
    }
}
