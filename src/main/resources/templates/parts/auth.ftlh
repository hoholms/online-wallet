<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="username" id="uname" placeholder="Username" required/>
            <label for="uname">Username</label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" name="password" id="passwd" placeholder="Password" required/>
            <label for="passwd">Password</label>
        </div>
        <#if isRegisterForm>
            <div class="form-floating mb-3">
                <input type="password" class="form-control" name="passwordConfirm" id="passwdConf"
                       placeholder="Password" required/>
                <label for="passwdConf">Confirm Password</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="firstName" id="fname" placeholder="First Name" required/>
                <label for="fname">First Name</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="lastName" id="lname" placeholder="Last Name" required/>
                <label for="lname">Last Name</label>
            </div>
            <div class="form-floating mb-3">
                <input type="email" class="form-control" name="email" id="email" placeholder="Email" required/>
                <label for="email">Email</label>
                <div id="emailHelp" class="form-text">We'll send you an activation link</div>
            </div>
        </#if>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>
                Register
            <#else>
                Login
            </#if>
        </button>
        <#if !isRegisterForm><a href="/register">Register</a><#else><a href="/login">Login</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-danger btn-sm">Sign Out</button>
    </form>
</#macro>