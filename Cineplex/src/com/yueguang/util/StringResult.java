package com.yueguang.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ServletRedirectResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class StringResult extends ServletRedirectResult {

	/**
	 * 
	 * @author Carmack Created on 2009-3-24 下午03:36:24
	 */

	private static final long serialVersionUID = -2800270132418148253L;

	private static final Logger LOG = LoggerFactory
			.getLogger(StringResult.class);

	public StringResult() {

		super();

	}

	public StringResult(String location) {

		super(location);

	}

	public void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {

		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(HTTP_RESPONSE);

		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(HTTP_REQUEST);

		response.setContentType("text/plain; charset=UTF-8");

		response.setHeader("Content-Disposition", "inline");

		PrintWriter writer = null;

		try {

			writer = response.getWriter();

			writer.write(request.getAttribute(finalLocation).toString());

		} catch (NullPointerException e) {

			if (finalLocation.equals("")) {

				LOG.warn("未指定value", e);

			} else {

				LOG.error("空", e);

			}

		} finally {

			if (writer != null) {

				writer.flush();

				writer.close();

			}

		}

	}

}
