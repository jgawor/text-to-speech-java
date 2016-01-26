/**
 * Copyright 2014 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.cloudoe.samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

@MultipartConfig
public class DemoServlet extends HttpServlet {
    
	private static Logger logger = Logger.getLogger(DemoServlet.class.getName());
	private static final long serialVersionUID = 1L;

	/**
	 * Forward the request to the index.jsp file if no parameters are specified.
	 *
	 * Invoke GET request to the Watson service otherwise
	 *
	 * @param req the req
	 * @param resp the resp
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("text") == null || req.getParameter("voice") == null) {
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		} else {
			boolean download = false;
			if (req.getParameter("download") != null && req.getParameter("download").equalsIgnoreCase("true")) {
				download = true;
			}

			req.setCharacterEncoding("UTF-8");
			try {
			    TextToSpeech textService = new TextToSpeech();
			    String text = req.getParameter("text");
			    String voiceParam = req.getParameter("voice");
			    Voice voice = new Voice(voiceParam, null, null);
                String format = "audio/ogg; codecs=opus";
                InputStream in = textService.synthesize(text, voice, format);
                				
				if (download) {				
					resp.setHeader("content-disposition", "attachment; filename=transcript.ogg");
				}
				ServletOutputStream servletOutputStream = resp.getOutputStream();
                byte[] buffer = new byte[2048];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    servletOutputStream.write(buffer, 0, read);
                }
				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "got error: " + e.getMessage(), e);
				resp.sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
			}
		}
	}

}
