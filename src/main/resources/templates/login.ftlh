<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1">
        <h2>Login</h2>
        <p class="text-info">${message!""}</p>
        <p class="text-danger">${error!""}</p>
    </div>
    <@l.auth "/login" false/>
</@c.page>