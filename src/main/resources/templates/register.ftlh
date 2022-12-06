<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1 text-center">
        <p class="text-danger">${error!""}</p>
    </div>
    <@l.auth "/register" true/>
</@c.page>