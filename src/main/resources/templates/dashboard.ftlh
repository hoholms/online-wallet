<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <h1>${user.username}'s dashboard</h1>

    <div>
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
                <form action="/dashboard" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <label>
                        <select class="form-control" name="category">
                            <#list incomeCategories as incomeCategory>
                                <option>${incomeCategory.category}</option>
                            </#list>
                        </select>
                    </label>
                    <label>
                        <input class="form-control" type="number" step="0.01" name="amount" placeholder="amount">
                    </label>
                    <label>
                        <input class="form-control" type="text" name="message" placeholder="Type message if u want">
                    </label>
                    <label>
                        <input class="form-control" type="date" name="transactionDate">
                    </label>
                    <input type="hidden" name="isIncome" value="true">
                    <button type="submit" class="btn btn-success">Submit</button>
                </form>
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
                <form action="/dashboard" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <label>
                        <select class="form-control" name="category">
                            <#list expenseCategories as expenseCategory>
                                <option>${expenseCategory.category}</option>
                            </#list>
                        </select>
                    </label>
                    <label>
                        <input class="form-control" type="number" step="0.01" name="amount" placeholder="amount">
                    </label>
                    <label>
                        <input class="form-control" type="text" name="message" placeholder="Type message if u want">
                    </label>
                    <label>
                        <input class="form-control" type="date" name="transactionDate">
                    </label>
                    <input type="hidden" name="isIncome" value="false">
                    <button type="submit" class="btn btn-success">Submit</button>
                </form>
            </div>
        </div>
    </div>

    <div>
        <h3>Your Today's transactions:</h3>
        <div class="card-row">
            <#list recentTransactions as todaysTransaction>
                <#if todaysTransaction.isIncome>
                    <div class="card border-success mb-3" style="width: 20rem;">
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
                    <div class="card border-danger mb-3" style="width: 20rem;">
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