<%@ page import="com.proxy.YouTubeDownloader" %>
<%@ page import="com.proxy.Video" %>
<%@ page import="java.util.HashMap" %>

<html>
<head>
    <title>ProxyDemo</title>
</head>
<body>
    <h1>ProxyDemo</h1>

    <form action="servletproxy" method="get">
        <label for="action">Choose an action:</label>
        <select name="action" id="action">
            <option value="popular">Display Popular Videos</option>
            <option value="video">Display Video</option>
        </select>
        <br><br>
        <label for="videoId">Video ID (if displaying a specific video):</label>
        <input type="text" name="videoId" id="videoId">
        <br><br>
        <input type="submit" value="Submit">
    </form>

    <c:if test="${not empty popularVideos}">
        <h2>Popular Videos:</h2>
        <ul>
            <c:forEach var="video" items="${popularVideos}">
                <li>${video.id} - ${video.title}</li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${not empty video}">
        <h2>Video:</h2>
        <p>ID: ${video.id}</p>
        <p>Title: ${video.title}</p>
        <p>Data: ${video.data}</p>
    </c:if>
</body>
</html>