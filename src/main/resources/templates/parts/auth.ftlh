<#macro auth path isRegisterForm>
    <div class="col-md-10 mx-auto col-lg-8">
        <form action="${path}" method="post" class="p-4 border rounded-3 bg-light-subtle">
            <#--noinspection FtlReferencesInspection-->
            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <h1 class="h2 mb-3 fw-normal text-center">
                <#if !isRegisterForm>
                    Login
                <#else>
                    Register
                </#if>
            </h1>
            <#if !isRegisterForm>
                <div class="text-center">
                    <#if message??>
                        <p class="alert alert-info" role="alert">${message!""}</p>
                    </#if>
                    <#if error?? && RequestParameters.error??>
                        <p class="alert alert-danger" role="alert">${error!""}</p>
                    </#if>
                </div>
            </#if>
            <div class="form-floating mb-3">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}" name="username"
                       id="uname" placeholder="Username"
                       value="<#if userDto??>${userDto.username!""}</#if>"/>
                <label for="uname">Username</label>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       name="password" id="passwd" maxlength="500"
                       placeholder="Password"/>
                <label for="passwd">Password</label>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
            <#if isRegisterForm>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                           name="passwordConfirm" maxlength="500" id="passwdConf"
                           placeholder="Password"/>
                    <label for="passwdConf">Confirm Password</label>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')}"
                           name="firstName" maxlength="50" id="fname"
                           placeholder="First Name" value="<#if profileDto??>${profileDto.firstName!""}</#if>"/>
                    <label for="fname">First Name</label>
                    <#if firstNameError??>
                        <div class="invalid-feedback">
                            ${firstNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control ${(lastNameError??)?string('is-invalid', '')}"
                           name="lastName" maxlength="50" id="lname"
                           placeholder="Last Name" value="<#if profileDto??>${profileDto.lastName!""}</#if>"/>
                    <label for="lname">Last Name</label>
                    <#if lastNameError??>
                        <div class="invalid-feedback">
                            ${lastNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" name="email"
                           id="email" maxlength="255" placeholder="Email"
                           value="<#if profileDto??>${profileDto.email!""}</#if>"/>
                    <label for="email">Email</label>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
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