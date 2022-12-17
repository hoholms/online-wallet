<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row align-items-center">
        <div class="col-md-10 mx-auto col-lg-8">
            <form method="post" class="p-4 border rounded-3 bg-light">
                <input type="hidden" name="_csrf" value="${_csrf.token}">

                <h1 class="h2 mb-3 fw-normal text-center">${username}</h1>
                <#if message??>
                    <p class="alert alert-success" role="alert">${message!""}</p>
                </#if>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')}"
                           name="firstName" id="fname"
                           placeholder="First Name"
                           value="${profileDto.firstName}"/>
                    <label for="fname">First Name</label>
                    <#if firstNameError??>
                        <div class="invalid-feedback">
                            ${firstNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control ${(lastNameError??)?string('is-invalid', '')}"
                           name="lastName" id="lname"
                           placeholder="Last Name"
                           value="${profileDto.lastName}"/>
                    <label for="lname">Last Name</label>
                    <#if lastNameError??>
                        <div class="invalid-feedback">
                            ${lastNameError}
                        </div>
                    </#if>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" name="email"
                           id="email" placeholder="Email"
                           value="${profileDto.email}"/>
                    <label for="email">Email</label>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                    <div id="emailHelp" class="form-text">We'll send you an activation link</div>
                </div>
                <div class="form-floating mb-3">
                    <select class="form-select" id="currency" name="currency" required>
                        <#list currencies as enumCurrency>
                            <option <#if enumCurrency.toString() == profileDto.currency>selected="selected"</#if>>${enumCurrency.toString()}</option>
                        </#list>
                    </select>
                    <label class="form-label" for="currency">Currency</label>
                </div>

                <button class="w-100 btn btn-lg btn-warning mb-3" type="button" data-bs-toggle="collapse"
                        data-bs-target="#passwordCollapse"
                        aria-expanded="<#if oldPasswordError?? || newPasswordError?? || confirmPasswordError??>true<#else>false</#if>"
                        aria-controls="passwordCollapse">
                    Change password
                </button>
                <div class="collapse mb-3 <#if oldPasswordError?? || newPasswordError?? || confirmPasswordError??>show</#if>"
                     id="passwordCollapse">
                    <div class="card card-body">
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control ${(oldPasswordError??)?string('is-invalid', '')}"
                                   name="oldPassword" id="oldPasswd" maxlength="500"
                                   placeholder="Password"/>
                            <label for="oldPasswd">Old password</label>
                            <#if oldPasswordError??>
                                <div class="invalid-feedback">
                                    ${oldPasswordError}
                                </div>
                            </#if>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control ${(newPasswordError??)?string('is-invalid', '')}"
                                   name="newPassword" id="newPasswd" maxlength="500"
                                   placeholder="Password"/>
                            <label for="newPasswd">New password</label>
                            <#if newPasswordError??>
                                <div class="invalid-feedback">
                                    ${newPasswordError}
                                </div>
                            </#if>
                        </div>
                        <div class="form-floating">
                            <input type="password"
                                   class="form-control ${(confirmPasswordError??)?string('is-invalid', '')}"
                                   name="confirmPassword" id="confPasswd" maxlength="500"
                                   placeholder="Password"/>
                            <label for="confPasswd">Confirm password</label>
                            <#if confirmPasswordError??>
                                <div class="invalid-feedback">
                                    ${confirmPasswordError}
                                </div>
                            </#if>
                        </div>
                    </div>
                </div>

                <button type="submit" class="w-100 btn btn-lg btn-primary">Save</button>
            </form>
        </div>
    </div>
</@c.page>