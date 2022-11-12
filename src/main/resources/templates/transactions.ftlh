<#import "parts/common.ftlh" as c>

<@c.page>
        <#list transactions as transaction>
            ${transaction.id} : ${transaction.category_id} : ${transaction.price}
        </#list>
</@c.page>