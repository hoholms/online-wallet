<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row align-items-center">
        <div class="col-md-10 mx-auto col-lg-8">
            <form method="post" class="p-4 border rounded-3 bg-light text-center">
                <h1 class="h2 mb-3 fw-normal text-center">${username}</h1>
                <p class="text-success">${message!""}</p>
                <p class="text-danger">${error!""}</p>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" name="password" id="passwd" maxlength="500"
                           placeholder="Password"/>
                    <label for="passwd">Password</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="firstName" id="fname" maxlength="50"
                           placeholder="First Name"
                           value="${firstName}" required/>
                    <label for="fname">First Name</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="lastName" id="lname" maxlength="50"
                           placeholder="Last Name"
                           value="${lastName}" required/>
                    <label for="lname">Last Name</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" name="email" id="email" maxlength="255" placeholder="Email"
                           value="${email}"
                           required/>
                    <label for="email">Email</label>
                    <div id="emailHelp" class="form-text">We'll send you an activation link</div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" class="w-100 btn btn-lg btn-primary">Save</button>
            </form>
        </div>
    </div>
</@c.page>