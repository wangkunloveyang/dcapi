package com.datangwealth.modules.xxljob.jobhandler;

import org.springframework.stereotype.Component;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
/**
 *      测试用handler
 * @author wangkun
 *
 */
@Component
@JobHandler("testHandler")
public class TestHandler extends IJobHandler {
	@Override
	public ReturnT<String> execute(String params) throws Exception {
		return new ReturnT<String>(200, "成功");
	}

}
