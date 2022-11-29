<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="mb-1">
        <h2>${username}</h2>
    </div>
    <p class="text-success">${message!""}</p>
    <p class="text-danger">${error!""}</p>
    <form method="post">
        <div class="form-floating mb-3">
            <input type="password" class="form-control" name="password" id="passwd" placeholder="Password"/>
            <label for="passwd">Password</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="firstName" id="fname" placeholder="First Name"
                   value="${firstName}" required/>
            <label for="fname">First Name</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="lastName" id="lname" placeholder="Last Name"
                   value="${lastName}" required/>
            <label for="lname">Last Name</label>
        </div>
        <div class="form-floating mb-3">
            <input type="email" class="form-control" name="email" id="email" placeholder="Email" value="${email}"
                   required/>
            <label for="email">Email</label>
            <div id="emailHelp" class="form-text">We'll send you an activation link</div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>