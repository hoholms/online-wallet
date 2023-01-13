<#import "parts/common.ftlh" as c>
<#import "parts/addTransaction.ftlh" as at>
<#include "parts/security.ftlh">

<@c.page>
<#--noinspection FtlReferencesInspection-->
    <h1>${currentProfile.firstName} ${currentProfile.lastName} dashboard</h1>

    <div class="container-fluid my-3">
        <h3>Your balance is
            <#if currentProfile.balance gt 0>
                <span class="text-success">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            <#elseif currentProfile.balance lt 0 >
                <span class="text-danger">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            <#else>
                <span class="text-muted">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            </#if>
        </h3>
    </div>

    <div class="container-fluid accordion" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    Add Income Transaction
                </button>
            </h2>
            <div id="collapseOne"
                 class="accordion-collapse collapse <#if errorsMap?? && transactionDto.isIncome>show</#if>"
                 aria-labelledby="headingOne"
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
            <div id="collapseTwo"
                 class="accordion-collapse collapse <#if errorsMap?? && !transactionDto.isIncome>show</#if>"
                 aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <@at.addTransaction expenseCategories false today />
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid my-4">
        <h3>In <span style="text-transform: lowercase">${today.month}</span> you:</h3>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-success">Earned ${monthIncome?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            and <span
                                    class="text-danger">spent ${monthExpense?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">${monthIncome?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        - <span
                                class="text-danger">${monthExpense?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        =
                        <#if monthIncome - monthExpense gt 0>
                            <span class="text-success">${(monthIncome - monthExpense)?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        <#elseif monthIncome - monthExpense lt 0>
                            <span class="text-danger">${(monthIncome - monthExpense)?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        <#else>
                            <span class="text-muted">${0?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-success">Earned the most money (${(maxIncomeCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            from ${maxIncomeCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">+${(maxIncomeCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                    </div>
                </div>
            </div>
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-danger">Spent the most money (${(maxExpenseCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            on ${maxExpenseCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-danger">-${(maxExpenseCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#if recentTransactions[0]??>
        <div class="container-fluid my-4">
            <h3 class="mb-4">Your recent transactions:</h3>
            <div class="row row-cols-1 row-cols-md-4 g-4" style="-webkit-transform: translateZ(0)">
                <#assign maxDate = recentTransactions[0].transactionDate>
                <hr class="col-md-12 mb-1">
                <h4 class="col-md-12">${maxDate.month?capitalize} ${maxDate.year?string.computer}
                </h4>
                <#list recentTransactions as transaction>
                    <#if transaction.transactionDate.month != maxDate.month ||
                    transaction.transactionDate.year != maxDate.year>
                        <#assign maxDate = transaction.transactionDate>
                        <hr class="col-md-12 mt-5">
                        <h4 class="col-md-12">${maxDate.month?capitalize} ${maxDate.year?string.computer}</h4>
                    </#if>
                    <#if transaction.isIncome>
                        <div class="col-sm-3">
                            <div class="card tran-card border-<#if transaction.transactionDate.isAfter(today)>secondary<#else>success</#if>"
                                 style="height: 100%;">
                                <div class="card-body <#if transaction.transactionDate.isAfter(today)>text-muted</#if>">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message?? && transaction.message != "">
                                        <p class="card-text ">${transaction.message!""}</p>
                                    <#else>
                                        <p class="card-text">
                                            Got ${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                            from ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-<#if transaction.transactionDate.isAfter(today)>muted<#else>success</#if>">
                                    +${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                    <span class="text-muted float-end">${transaction.transactionDate}</span>
                                    <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                                </div>
                                <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                            </div>
                        </div>
                    <#else>
                        <div class="col-sm-3">
                            <div class="card tran-card border-<#if transaction.transactionDate.isAfter(today)>secondary<#else>danger</#if>"
                                 style="height: 100%;">
                                <div class="card-body <#if transaction.transactionDate.isAfter(today)>text-muted</#if>">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message?? && transaction.message != "">
                                        <p class="card-text">${transaction.message!""}</p>
                                    <#else>
                                        <p class="card-text">
                                            Spent ${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                            on ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-<#if transaction.transactionDate.isAfter(today)>muted<#else>danger</#if>">
                                    -${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                    <span class="text-muted float-end">${transaction.transactionDate}</span>
                                    <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
                <hr class="col-md-12 mt-5">
            </div>
        </div>
    </#if>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/tilt.js/1.2.1/tilt.jquery.min.js"></script>
</@c.page>