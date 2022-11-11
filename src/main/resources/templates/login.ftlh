<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1">
        <h5>Login Page</h5>
        <p class="text-danger">${error!""}</p>
    </div>
    <@l.authLogin "/login" false/>
</@c.page>