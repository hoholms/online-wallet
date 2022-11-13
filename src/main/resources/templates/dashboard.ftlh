<#import "parts/common.ftlh" as c>
<#import "parts/addTransaction.ftlh" as at>
<#include "parts/security.ftlh">

<@c.page>
    <h1>${user.username}'s dashboard</h1>

    <div class="my-3">
        <h3>Your balance is
            <#if currentProfile.balance gt 0>
                <span class="text-success">${currentProfile.balance}</span>
            <#elseif currentProfile.balance lt 0 >
                <span class="text-danger">${currentProfile.balance}</span>
            <#else>
                <span class="text-muted">${currentProfile.balance}</span>
            </#if>
        </h3>
    </div>

    <div class="accordion" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    Add Income Transaction
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <@at.addTransaction incomeCategories true today />
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Add Expense Transaction
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <@at.addTransaction expenseCategories false today />
                </div>
            </div>
        </div>
    </div>

    <div class="mt-4">
        <h3>Your recent transactions:</h3>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <#list recentTransactions as todaysTransaction>
                <#if todaysTransaction.isIncome>
                    <div class="col">
                        <div class="card border-success">
                            <div class="card-body">
                                <h5 class="card-title">${todaysTransaction.category.category}</h5>
                                <#if todaysTransaction.message != "">
                                    <p class="card-text">${todaysTransaction.message}</p>
                                <#else>
                                    <p class="card-text">Got ${todaysTransaction.amount}
                                        from ${todaysTransaction.category.category}.</p>
                                </#if>
                            </div>
                            <div class="card-footer text-success">
                                +${todaysTransaction.amount}
                                <span class="text-muted float-end">${todaysTransaction.transactionDate}</span>
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="card border-danger">
                            <div class="card-body">
                                <h5 class="card-title">${todaysTransaction.category.category}</h5>
                                <#if todaysTransaction.message != "">
                                    <p class="card-text">${todaysTransaction.message}</p>
                                <#else>
                                    <p class="card-text">Spent ${todaysTransaction.amount}
                                        to ${todaysTransaction.category.category}.</p>
                                </#if>
                            </div>
                            <div class="card-footer text-danger">
                                -${todaysTransaction.amount}
                                <span class="text-muted float-end">${todaysTransaction.transactionDate}</span>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>
</@c.page>