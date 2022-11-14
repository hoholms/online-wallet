<#import "parts/common.ftlh" as c>


<@c.page>

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
                            <a href="/transactions/${transaction.id}" class="stretched-link"></a>
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
                            <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>
</@c.page>