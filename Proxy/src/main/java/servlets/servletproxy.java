package servlets;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.proxy.Video;
import com.proxy.YouTubeDownloader;
import com.proxy.ThirdPartyYouTubeLib;
import com.proxy.ThirdPartyYouTubeClass;
import com.proxy.YouTubeCacheProxy;

@WebServlet("/servletproxy")
public class servletproxy extends HttpServlet {
    private YouTubeDownloader naiveDownloader;
    private YouTubeDownloader smartDownloader;

    @Override
    public void init() throws ServletException {
        ThirdPartyYouTubeLib naiveLib = new ThirdPartyYouTubeClass();
        naiveDownloader = new YouTubeDownloader(naiveLib);
        ThirdPartyYouTubeLib smartLib = new YouTubeCacheProxy();
        smartDownloader = new YouTubeDownloader(smartLib);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'action' parameter");
            return;
        }

        switch (action) {
            case "popular":
                handlePopularRequest(request, response);
                break;
            case "video":
                handleVideoRequest(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'action' parameter");
        }
    }

    private void handlePopularRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HashMap<String, Video> popularVideos = smartDownloader.renderPopularVideos();
        request.setAttribute("popularVideos", popularVideos);
        try {
			request.getRequestDispatcher("/popular.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void handleVideoRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String videoId = request.getParameter("videoId");
        if (videoId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'videoId' parameter");
            return;
        }
        
        Video video = smartDownloader.getVideo(videoId);
        request.setAttribute("video", video);
        try {
			request.getRequestDispatcher("/video.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}