<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1">
        <h4>Login</h4>
        <p class="text-danger">${error!""}</p>
    </div>
    <@l.auth "/login" false/>
</@c.page>