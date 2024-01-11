var sidebarOpen = false;
var savedQueries = {}; // Object to store saved queries and their respective results

function toggleSidebar() {
    if (sidebarOpen) {
        document.getElementById("sidebar").style.width = "0";
        document.getElementById("main-content").style.marginLeft = "0";
        document.getElementById("open-sidebar-btn").style.display = "block"; // Show the button when sidebar is closed
        sidebarOpen = false;
    } else {
        document.getElementById("sidebar").style.width = "250px";
        document.getElementById("main-content").style.marginLeft = "250px";
        document.getElementById("open-sidebar-btn").style.display = "none"; // Hide the button when sidebar is open
        sidebarOpen = true;
    }
}

function openSidebar() {
    if (!sidebarOpen) {
        toggleSidebar();
    }
}

function saveSearchResult() {
    var searchQuery = document.getElementById('search-box').value;
    if (searchQuery && lastSearchResult) {
        var resultId = new Date().getTime(); 
        savedQueries[resultId] = {
            query: searchQuery,
            result: lastSearchResult
        };

        var savedResult = document.createElement('div');
        savedResult.className = 'sidebar-saved-result';
        savedResult.textContent = searchQuery;
        savedResult.setAttribute('data-result-id', resultId);
        savedResult.onclick = function() {
            displaySavedResult(resultId);
        };
        document.getElementById('sidebar').appendChild(savedResult);
    }
}


function displaySavedResult(resultId) {
    var savedQuery = savedQueries[resultId];
    if (savedQuery) {
        displaySearchResults(savedQuery.result); 
    }
}


document.addEventListener('DOMContentLoaded', function() {
    var searchBox = document.getElementById('search-box');
    var searchButton = document.getElementById('search-button');
    searchButton.addEventListener('click', function(event) {
        var query = searchBox.value;
        performSearch(query+" 鬼片");
        searchBox.value = searchBox.value ; 
    });    
    searchBox.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            var query = this.value;
            performSearch(query+" 鬼片"); // 使用AJAX請求進行搜索
            this.value = this.value;
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var suggestionContainer = document.getElementById('suggestion-container');
    var searchBox = document.getElementById('search-box');

    // Sample suggestions
    var suggestions = ['\u9B3C\u7247', '\u90C1\u65B9', '\u5A03\u5A03', '\u5492\u9748'];



    // Function to create and append suggestion buttons
    function createSuggestionButton(suggestion) {
        var btn = document.createElement('button');
        btn.style.width = '133.48px';
    	btn.style.height = '38px';
        btn.className = 'suggestion-btn';
        btn.textContent = suggestion;
        btn.onclick = function() {
            searchBox.value = suggestion; // Fill in the search bar with the suggestion
        };
        suggestionContainer.appendChild(btn);
    }
    suggestions.forEach(createSuggestionButton);
});
var lastSearchResult = ''; 

function performSearch(query) {
    var xhr = new XMLHttpRequest();
    var url = 'index?keyword=' + encodeURIComponent(query);
    xhr.open('GET', url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            lastSearchResult = xhr.responseText;
            displaySearchResults(xhr.responseText);
            saveSearchResult();
        } else {
            console.error('Error fetching data');
        }
    };
    xhr.onerror = function() {
        console.error('Network error occurred');
    };
    xhr.send();
}



function displaySearchResults(responseText) {
    var resultsContainer = document.getElementById('search-results');
    resultsContainer.innerHTML = '';

    var lines = responseText.split('\n');
    lines.forEach(function(line) {
        var parts = line.split(',');
        if (parts.length == 3) {
            var resultDiv = document.createElement('div');
            resultDiv.className = 'search-result';
            resultDiv.textContent = parts[0] + ' - Score: ' + parts[2]; 
            resultDiv.style.cursor = 'pointer'; 
            resultDiv.onclick = function() {
                window.open(parts[1], '_blank'); 
            };

            resultsContainer.appendChild(resultDiv);
        }
    });
    
}


