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
    if (searchQuery) {
        // Save the result in the object with a unique ID
        var resultId = new Date().getTime(); // Using timestamp as a unique ID
        savedQueries[resultId] = searchQuery;
        console.log(savedQueries)

        // Create sidebar item for the saved result
        var savedResult = document.createElement('div');
        savedResult.className = 'sidebar-saved-result';
        savedResult.textContent = searchQuery;
        savedResult.setAttribute('data-result-id', resultId); // Set a data attribute with the result ID
        savedResult.onclick = function() { displaySavedResult(resultId); }; // Add click event to display the result
        document.getElementById('sidebar').appendChild(savedResult);

        
    }
}

function displaySavedResult(resultId) {
    // Get the saved query based on the result ID
    var savedQuery = savedQueries[resultId];
    var resultsContainer = document.getElementById('search-results');
    resultsContainer.innerHTML = ''; // Clear previous results

    // Display the saved search result
    var resultDiv = document.createElement('div');
    resultDiv.className = 'search-result';
    resultDiv.innerHTML = '<p>You searched for: ' + savedQuery + '</p>';
    resultsContainer.appendChild(resultDiv);
}

document.addEventListener('DOMContentLoaded', function() {
    var searchBox = document.getElementById('search-box');
    var searchButton = document.getElementById('search-button');
    var resultsContainer = document.getElementById('search-results');
    // Clear the search box and current search results
    //document.getElementById('search-box').value = '';
    

    // Event listener for the Save button
    searchButton.addEventListener('click', function(event) {
        // Clear the search box and current search results
        var query = searchBox.value;
        performSearch(query); // 使用AJAX請求進行搜索
        /*
        document.getElementById('search-results').innerHTML = '';
        var resultDiv = document.createElement('div');
        resultDiv.className = 'search-result';
        resultDiv.innerHTML = '<p>You searched for: ' + searchBox.value + '</p>';
*/
        // Append the new search result div to the container
        //resultsContainer.appendChild(resultDiv);
        // Clear the search box after the search
        this.value = this.value;
        saveSearchResult(); // Call the save function to store the result and clear the fields
    });

    // Event listener for the Enter key in the search box
    searchBox.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            var query = this.value;
            performSearch(query); // 使用AJAX請求進行搜索
            // Clear the search box and current search results
            /*
            document.getElementById('search-results').innerHTML = '';
            var resultDiv = document.createElement('div');
            resultDiv.className = 'search-result';
            resultDiv.innerHTML = '<p>You searched for: ' + this.value + '</p>';
*/
            // Append the new search result div to the container
            //resultsContainer.appendChild(resultDiv);
            // Clear the search box after the search
            this.value = this.value;
            saveSearchResult(); // Call the save function to store the result and clear the fields
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var suggestionContainer = document.getElementById('suggestion-container');
    var searchBox = document.getElementById('search-box');

    // Sample suggestions
    var suggestions = ['First suggestion', 'Second suggestion', 'Third suggestion', 'Fourth suggestion'];

    // Function to create and append suggestion buttons
    function createSuggestionButton(suggestion) {
        var btn = document.createElement('button');
        btn.className = 'suggestion-btn';
        btn.textContent = suggestion;
        btn.onclick = function() {
            searchBox.value = suggestion; // Fill in the search bar with the suggestion
        };
        suggestionContainer.appendChild(btn);
    }

    // Create suggestion buttons
    suggestions.forEach(createSuggestionButton);
});
/*
function performSearch(query) {
    var xhr = new XMLHttpRequest();
    // encodeURIComponent 函数只需要一个参数
    xhr.open('GET', 'https://www.google.com/search?q=' + encodeURIComponent(query), true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(xhr.responseText); 
            var resultsContainer = document.getElementById('search-results');
            resultsContainer.innerHTML = xhr.responseText;
        } else {
            console.error('Error fetching data: ' + xhr.status);
        }
    };
    xhr.onerror = function() {
        console.error('Network error occurred');
    };
    xhr.send();
}*/
function performSearch(query) {
    var xhr = new XMLHttpRequest();
    var url = 'index?keyword=' + encodeURIComponent(query);
    xhr.open('GET', url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(xhr.responseText);
           	if (typeof xhr.responseText !== 'string') {
        console.error('Response is not a string:', responseText);
        return;
   }
            displaySearchResults(xhr.responseText);
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
    console.log(lines);
    lines.forEach(function(line) {
        var parts = line.split(',');
        if (parts.length == 3) {
            var resultDiv = document.createElement('div');
            resultDiv.className = 'search-result';

            var link = document.createElement('a');
            link.href = parts[1]; 
            link.textContent = parts[0]; 
            link.target = '_blank'; 

            resultDiv.appendChild(link);
            resultDiv.appendChild(document.createTextNode(' Score: ' + parts[2]));

            resultsContainer.appendChild(resultDiv);
        }
    });
}

