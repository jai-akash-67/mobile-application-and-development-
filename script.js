<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>G-Collector</title>
<link rel="stylesheet" href="style.css">
</head>

<body>

<!-- SPLASH -->
<div id="splash" class="screen active">
<h1 class="logo">🌿 G-Collector</h1>
</div>

<!-- LOGIN -->
<div id="login" class="screen">
<h2>Welcome Back</h2>
<input id="user" placeholder="Username">
<input id="pass" type="password" placeholder="Password">
<button onclick="login()">Login</button>
<button onclick="show('signup')">Sign Up</button>
</div>

<!-- SIGNUP -->
<div id="signup" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Create Account</h2>
<input id="newUser" placeholder="Username">
<input id="newPass" placeholder="Password">
<button onclick="signup()">Create</button>
</div>

<!-- TERMS -->
<div id="terms" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Terms</h2>
<p>You agree to share waste data for smart collection system.</p>
<button onclick="show('details')">Accept</button>
<button onclick="alert('You must accept')">Cancel</button>
</div>

<!-- DETAILS -->
<div id="details" class="screen">
<button onclick="goBack()">⬅</button>
<h2>User Details</h2>
<input id="name" placeholder="Name">
<input id="email" placeholder="Email">
<input id="phone" placeholder="Phone">
<input id="address" placeholder="Address">
<button onclick="saveDetails()">Next</button>
</div>

<!-- LOCATION -->
<div id="location" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Set Location</h2>
<div id="map" class="map">Map</div>
<button onclick="setLocation()">Detect</button>
<button onclick="show('home')">Continue</button>
</div>

<!-- HOME -->
<div id="home" class="screen">
<h1>🌿 G-Collector</h1>

<div class="card">
E-Coins: <span id="coins">0</span><br>
<p id="homeLocation"></p>
</div>

<button class="big-btn" onclick="request()">♻️ REQUEST</button>

<div class="grid">
<button onclick="show('dashboard')">📅 Dashboard</button>
<button onclick="show('community')">👥 Community</button>
<button onclick="show('profile')">👤 Profile</button>
<button onclick="show('historyPage')">🧾 History</button>
</div>
</div>

<!-- TRACK -->
<div id="tracking" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Collector Coming 🚛</h2>
<div class="truck">🚛</div>
<p id="distance"></p>
</div>

<!-- CART -->
<div id="cart" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Scrap</h2>
<div id="items"></div>
<h3>Total ₹ <span id="total">0</span></h3>
<button onclick="invoice()">Generate Invoice</button>
</div>

<!-- INVOICE -->
<div id="invoice" class="screen">
<button onclick="show('home')">🏠</button>
<div id="bill"></div>
</div>

<!-- DASHBOARD -->
<div id="dashboard" class="screen">
<button onclick="goBack()">⬅</button>

<div class="cal-nav">
<button onclick="prevMonth()">⬅</button>
<h2 id="monthYear"></h2>
<button onclick="nextMonth()">➡</button>
</div>

<div id="calendar"></div>

<button onclick="show('analytics'); drawChart()">Analytics</button>
</div>

<!-- ANALYTICS -->
<div id="analytics" class="screen">
<button onclick="goBack()">⬅</button>
<canvas id="chart" width="300" height="200"></canvas>
</div>

<!-- HISTORY -->
<div id="historyPage" class="screen">
<button onclick="goBack()">⬅</button>
<h2>History</h2>
<div id="historyList"></div>
</div>

<!-- COMMUNITY -->
<div id="community" class="screen">
<button onclick="goBack()">⬅</button>
<h2>Community</h2>
<div id="posts"></div>
<button onclick="addPost()">Add Post</button>
</div>

<!-- PROFILE -->
<div id="profile" class="screen">
<button onclick="goBack()">⬅</button>

<div class="card">
<img src="https://via.placeholder.com/80" style="border-radius:50%">
<h2 id="pname"></h2>
<p id="pemail"></p>
<p id="pphone"></p>
<p id="paddress"></p>
</div>

<button onclick="editProfile()">Edit</button>
<button onclick="toggleMode()">Theme</button>
<button onclick="logout()">Logout</button>
</div>

<script src="script.js"></script>
</body>
</html>