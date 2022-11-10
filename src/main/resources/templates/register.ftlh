<#import "parts/common.ftlh" as lr>
<@lr.common>
<form action="/register" method="post">
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>
        <label for="username">
            <b>Username</b>
        </label>
        <input id="username" name="username" placeholder="Enter Username" required type="text">
        <label for="password"><b>Password</b></label>
        <input id="password" name="password" placeholder="Enter Password" required type="password">
        <input type="submit" value="Sign Up">
    </div>
</form>
<form action="/login">
    <input type="submit" value="Login page">
</form>
</@lr.common>
