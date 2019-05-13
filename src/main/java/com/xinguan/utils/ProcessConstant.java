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
        }
    }
}
