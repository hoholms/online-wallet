/*** Charts html elements ***/
const lineStatistics = document.getElementById('lineChart').getContext("2d");
const incomeCircleStatistics = document.getElementById('incomeCircleChart');
const expenseCircleStatistics = document.getElementById('expenseCircleChart');

/*** Dates selection ***/
var fromData = document.getElementById("from");
var toData = document.getElementById("to");

/*** Gradient ***/
var greenGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
greenGradient.addColorStop(0, 'rgba(25, 135, 84,1)');
greenGradient.addColorStop(1, 'rgba(25, 135, 84,0)');

var redGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
redGradient.addColorStop(0, 'rgba(165, 29, 42,1)');
redGradient.addColorStop(1, 'rgba(165, 29, 42,0)');

/*** Font ***/
Chart.defaults.font.family = document.getElementById('statTitle').style.fontFamily;
currencyFormat = new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: $('#currency').val()
});

/*** Charts ***/
var lineChart, incomeCircleChart, expenseCircleChart;
$.getJSON('/statistics/line', function (data) {
    drawLineStatistics(data);
    var sum = 0;
    $.each(data[0].values, function () {
        sum += parseFloat(this) || 0;
        sum.toFixed(2);
    });
    $('#totalEarned').text('Total earned: ' + currencyFormat.format(sum));
});
$.getJSON('/statistics/circle', function (data) {
    drawCircleStatistics(data);
    var sum = 0;
    $.each(data[1].values, function () {
        sum += parseFloat(this) || 0;
        sum.toFixed(2);
    });
    $('#totalSpent').text('Total spent: ' + currencyFormat.format(sum));
});
$('.statForm').change(function () {
    var token = $("meta[name='_csrf']").attr("content");

    $.ajax({
        url: '/statistics/line',
        type: 'post',
        data: {
            _csrf: token,
            from: fromData.options[fromData.selectedIndex].text,
            to: toData.options[toData.selectedIndex].text
        },
        dataType: 'json',
        success: function (data) {
            lineChart.data.labels = data[0].labels;
            lineChart.data.datasets[0].data = data[0].values;
            lineChart.data.datasets[1].data = data[1].values;
            lineChart.update();

            var sum = 0;
            $.each(data[0].values, function () {
                sum += parseFloat(this) || 0;
                sum.toFixed(2);
            });
            $('#totalEarned').text('Total earned: ' + currencyFormat.format(sum));
        }
    });

    $.ajax({
        url: '/statistics/circle',
        type: 'post',
        data: {
            _csrf: token,
            from: fromData.options[fromData.selectedIndex].text,
            to: toData.options[toData.selectedIndex].text
        },
        dataType: 'json',
        success: function (data) {
            incomeCircleChart.data.labels = data[0].categories;
            incomeCircleChart.data.datasets[0].data = data[0].values;
            incomeCircleChart.update();

            expenseCircleChart.data.labels = data[1].categories;
            expenseCircleChart.data.datasets[0].data = data[1].values;
            expenseCircleChart.update();

            var sum = 0;
            $.each(data[1].values, function () {
                sum += parseFloat(this) || 0;
                sum.toFixed(2);
            });
            $('#totalSpent').text('Total spent: ' + currencyFormat.format(sum));
        }
    });
});

/*** Draw chart functions ***/
function drawLineStatistics(data) {
    lineChart = new Chart(lineStatistics, {
        type: 'line',
        data: {
            labels: data[0].labels,
            datasets: [
                {
                    backgroundColor: greenGradient,
                    fill: true,
                    label: 'Earned',
                    data: data[0].values,
                    borderColor: 'rgb(25, 135, 84)',
                    pointBorderColor: 'rgb(25, 135, 84)',
                    pointBackgroundColor: 'rgb(25, 135, 84)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                },
                {
                    backgroundColor: redGradient,
                    fill: true,
                    label: 'Spent',
                    data: data[1].values,
                    borderColor: 'rgb(165, 29, 42)',
                    pointBorderColor: 'rgb(165, 29, 42)',
                    pointBackgroundColor: 'rgb(165, 29, 42)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                }
            ]
        },
        options: {
            responsive: true,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            stacked: false,
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += currencyFormat.format(context.parsed.y);
                            }
                            return label;
                        }
                    }
                }
            },
            layout: {
                padding: 10
            },
            scales: {
                x: {
                    grid: {
                        display: true,
                    }
                },
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',

                    grid: {
                        display: true,
                    }
                },
            }
        },
    });
}

function drawCircleStatistics(data) {
    incomeCircleChart = new Chart(incomeCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[0].categories,
            datasets: [
                {
                    label: 'Earned',
                    data: data[0].values,
                    backgroundColor: [
                        'rgb(25, 135, 84)',
                        'rgb(13,96,0)',
                        'rgb(143,188,143)',
                        'rgb(0,250,154)',
                        'rgb(50,205,50)',
                        'rgb(20, 108, 67)',
                        'rgb(128,128,0)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
                },
            ]
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += currencyFormat.format(context.parsed);
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });

    expenseCircleChart = new Chart(expenseCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[1].categories,
            datasets: [
                {
                    label: 'Spent',
                    data: data[1].values,
                    backgroundColor: [
                        'rgb(165, 29, 42)',
                        'rgb(128, 0, 32)',
                        'rgb(170, 74, 68)',
                        'rgb(210, 4, 45)',
                        'rgb(227, 66, 52)',
                        'rgb(129, 65, 65)',
                        'rgb(119, 7, 55)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
                },
            ]
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed !== null) {
                                label += currencyFormat.format(context.parsed);
                            }
                            return label;
                        }
                    }
                }
            }
        }
    });
}