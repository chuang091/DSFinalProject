<%@ page language="java" contentType="text/html; charset=Big5" pageEncoding="Big5"%>
<!DOCTYPE html>
<html lang="zh-tw">
<head>
    <meta charset="Big5">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Engine UI</title>
    <link rel="stylesheet" href="styles.css"> <!-- Update the href path as needed -->
</head>
<body>
    <div id="sidebar">
        <a href="javascript:void(0)" class="closebtn" onclick="toggleSidebar()">&times;</a>
        <!-- Additional sidebar content here -->
    </div>
    <div id="main-content">
        <div id="suggestion-container">
            <!-- Suggestion buttons will be added here -->
        </div>
        <div id="search-container">
            <input type="text" id="search-box" placeholder="Search...">
            <button id="search-button">Search</button> <!-- Search button added here -->
        </div>
        <div id="search-results"></div> <!-- Search results will be dynamically added here -->
    </div>
    <button id="open-sidebar-btn" onclick="openSidebar()">&#9776;</button>
    <script src="scripts.js" charset="UTF-8"></script> <!-- Update the src path as needed -->
</body>
</html>
