<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="container-fluid mb-4">
        <h2 id="statTitle">Statistics</h2>
        <div class="container d-flex justify-content-center mb-3">
            <form action="/statistics/line" method="post" class="row g-3">
                <input type="hidden" name="_csrf" value="${_csrf.token}">

                <div class="col-6 form-floating">
                    <select class="form-select statForm" id="from" name="from">
                        <#list dates as date>
                            <option>${date.label}</option>
                        </#list>
                    </select>
                    <label class="form-label" for="from">From</label>
                </div>

                <div class="col-6 form-floating">
                    <select class="form-select statForm" id="to" name="to">
                        <#list dates as date>
                            <option <#if !date_has_next>selected="selected"</#if>>${date.label}</option>
                        </#list>
                    </select>
                    <label class="form-label" for="to">To</label>
                </div>
            </form>
        </div>

        <div class="row d-flex justify-content-center">
            <div class="col-md-10 float-start mb-4">
                <canvas id="lineChart"></canvas>
            </div>
            <div class="col-md-5 text-center mb-4">
                <h3 class="text-success" id="totalEarned">Total earned: </h3>
            </div>
            <div class="col-md-5 text-center mb-4">
                <h3 class="text-danger" id="totalSpent">Total spent: </h3>
            </div>
        </div>

        <div class="row d-flex justify-content-center">
            <div class="col-md-5 float-end mb-4">
                <canvas id="incomeCircleChart" class="col-sm"></canvas>

            </div>
            <div class="col-md-5 float-end mt-4 mb-4">
                <canvas id="expenseCircleChart" class="col-sm"></canvas>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"
            integrity="sha512-ElRFoEQdI5Ht6kZvyzXhYG9NqjtkmlkfYk0wr6wHxU9JEHakS7UJZNeml5ALk+8IKlU6jDgMabC3vkumRokgJA=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>


</@c.page>
<script type="text/javascript" src="/static/statistics.js"></script>