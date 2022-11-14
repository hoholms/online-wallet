<#import "parts/common.ftlh" as c>


<@c.page>
<#--    <h4>List of transactions</h4>-->
<#--    <table class="table table-hover">-->
<#--        <thead>-->
<#--        <tr>-->
<#--            <th>ID</th>-->
<#--            <th>Category</th>-->
<#--            <th>Type</th>-->
<#--            <th>Amount</th>-->
<#--            <th>Message</th>-->
<#--            <th>Transaction Date</th>-->
<#--            <th>Edit</th>-->
<#--            <th>Delete</th>-->
<#--        </tr>-->
<#--        </thead>-->
<#--        <tbody>-->
<#--        <#list transactions as transaction>-->
<#--            <tr>-->
<#--                <td>${transaction.id}</td>-->
<#--                <td>${transaction.category.category}</td>-->
<#--                <td>${transaction.isIncome?string("Income", "Expanse")}</td>-->
<#--                <td>${transaction.amount} MDL</td>-->
<#--                <td>${transaction.message}</td>-->
<#--                <td>${transaction.transactionDate}</td>-->
<#--                <td><a class="btn btn-outline-primary btn-sm" href="/transactions/${transaction.id}"-->
<#--                       role="button">Edit</a></td>-->
<#--                <td><a class="btn btn-outline-danger btn-sm" href="/transactions/delete/${transaction.id}"-->
<#--                       role="button">Delete</a></td>-->
<#--            </tr>-->
<#--        </#list>-->
<#--        </tbody>-->
<#--    </table>-->

    <div class="container-fluid my-4">
        <h3>All your transactions:</h3>
        <br>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <#list transactions as transaction>
                <#if transaction.isIncome>
                    <div class="col">
                        <div class="card border-success">
                            <div class="card-body">
                                <h5 class="card-title">${transaction.category.category}</h5>
                                <#if transaction.message != "">
                                    <p class="card-text">${transaction.message}</p>
                                <#else>
                                    <p class="card-text">Got ${transaction.amount}
                                        from ${transaction.category.category}.</p>
                                </#if>
                            </div>
                            <div class="card-footer text-success">
                                +${transaction.amount}
                                <span class="text-muted float-end">${transaction.transactionDate}</span>
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="card border-danger">
                            <div class="card-body">
                                <h5 class="card-title">${transaction.category.category}</h5>
                                <#if transaction.message != "">
                                    <p class="card-text">${transaction.message}</p>
                                <#else>
                                    <p class="card-text">Spent ${transaction.amount}
                                        to ${transaction.category.category}.</p>
                                </#if>
                            </div>
                            <div class="card-footer text-danger">
                                -${transaction.amount}
                                <span class="text-muted float-end">${transaction.transactionDate}</span>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>
</@c.page>