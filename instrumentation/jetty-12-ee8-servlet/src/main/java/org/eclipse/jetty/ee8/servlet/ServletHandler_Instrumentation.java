package org.eclipse.jetty.ee8.servlet;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.jetty.ee8.servlet.ServerHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.ee8.nested.Request;

@Weave(originalName = "org.eclipse.jetty.ee8.servlet.ServletHandler")
public class ServletHandler_Instrumentation {

    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        boolean isStarted = AgentBridge.getAgent().getTransaction().isStarted();

        boolean startTransaction = request != null && !isStarted;

        if (startTransaction) {
            ServerHelper.preHandle(baseRequest, request, response);
        }
        try {
            Weaver.callOriginal();
        } finally {
            if (startTransaction) {
                ServerHelper.postHandle(request);
            }
        }
    }
}
