<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
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
            </div>
        </#if>
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>
                Sign Up
            <#else>
                Sign In
            </#if>
        </button>
        <#if !isRegisterForm><a href="/register">Sign Up</a><#else><a href="/login">Sign In</a></#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-danger btn-sm">Sign Out</button>
    </form>
</#macro>