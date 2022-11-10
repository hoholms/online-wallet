<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label for="uname">Username</label>
            <input type="text" class="form-control" name="username" id="uname" placeholder="Username"/>
        </div>
        <div class="form-group">
            <label for="passwd">Password</label>
            <input type="password" class="form-control" name="password" id="passwd" placeholder="Password"/>
        </div>
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
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-danger btn-sm">Sign Out</button>
    </form>
</#macro>