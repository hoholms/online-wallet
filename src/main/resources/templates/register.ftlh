<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1"><h4>Register</h4></div>
    <p class="text-danger">${error!""}</p>
    <@l.auth "/register" true/>
</@c.page>