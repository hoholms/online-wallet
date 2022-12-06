<#macro auth path isRegisterForm>
    <div class="col-md-10 mx-auto col-lg-8">
        <form action="${path}" method="post" class="p-4 border rounded-3 bg-light">
            <#--noinspection FtlReferencesInspection-->
            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <h1 class="h2 mb-3 fw-normal text-center">
                <#if !isRegisterForm>
                    Login
                <#else>
                    Register
                </#if>
            </h1>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" name="username" id="uname" placeholder="Username" required/>
                <label for="uname">Username</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control" name="password" id="passwd" placeholder="Password"
                       required/>
                <label for="passwd">Password</label>
            </div>
            <#if isRegisterForm>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" name="passwordConfirm" id="passwdConf"
                           placeholder="Password" required/>
                    <label for="passwdConf">Confirm Password</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="firstName" id="fname" placeholder="First Name"
                           required/>
                    <label for="fname">First Name</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="lastName" id="lname" placeholder="Last Name"
                           required/>
                    <label for="lname">Last Name</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" name="email" id="email" placeholder="Email" required/>
                    <label for="email">Email</label>
                    <div id="emailHelp" class="form-text">We'll send you an activation link</div>
                </div>
            </#if>
            <button type="submit" class="btn btn-lg btn-primary w-100">
                <#if isRegisterForm>
                    Register
                <#else>
                    Login
                </#if>
            </button>
            <div class="mt-3 text-center">
                <#if !isRegisterForm>
                    <a href="/register">Don't have an account? Register</a>
                <#else>
                    <a href="/login">Already have an account? Login</a>
                </#if>
            </div>
        </form>
    </div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <#--noinspection FtlReferencesInspection-->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-danger btn-sm">Sign Out</button>
    </form>
</#macro>