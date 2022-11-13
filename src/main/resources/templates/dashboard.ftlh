<#import "parts/common.ftlh" as c>
<#import "parts/addTransaction.ftlh" as at>
<#include "parts/security.ftlh">

<@c.page>
    <h1>${user.username}'s dashboard</h1>

    <div class="mt-3">
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

    <div id="accordion">
    <div class="card">
        <div class="card-header" id="headingOne">
            <h5 class="mb-0">
                <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                        aria-controls="collapseOne">
                    Add Income Transaction
                </button>
            </h5>
        </div>

        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
            <div class="card-body">
                <@at.addTransaction incomeCategories true today />
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header" id="headingTwo">
            <h5 class="mb-0">
                <button class="btn btn-link" data-toggle="collapse" data-target="#collapseTwo"
                        aria-expanded="false" aria-controls="collapseTwo">
                    Add Expense Transaction
                </button>
            </h5>
        </div>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
            <div class="card-body">
                <@at.addTransaction expenseCategories false today />
            </div>
        </div>
    </div>

    <div class="mt-5">
        <h3>Your recent transactions:</h3>
        <div class="card-columns">
            <#list recentTransactions as todaysTransaction>
                <#if todaysTransaction.isIncome>
                    <div class="card border-success my-3" style="width: 20rem;">
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
                            <span class="text-muted float-right">${todaysTransaction.transactionDate}</span>
                        </div>
                    </div>
                <#else>
                    <div class="card border-danger my-3" style="width: 20rem;">
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
                            <span class="text-muted float-right">${todaysTransaction.transactionDate}</span>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>
</@c.page>