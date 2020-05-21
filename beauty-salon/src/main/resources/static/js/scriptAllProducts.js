let mainUrl = "http://localhost:8080";
// let mainUrl = "http://localhost:8000";

makeRequestProduct();
makeRequestCategory();

// $('#pagination-confirm').click(makeRequest);

$('#page').change(makeRequestProduct);
$('#size').change(makeRequestProduct);
$('#direction').change(makeRequestProduct);
$('#fieldName').change(makeRequestProduct);
$('#categoriesId').change(makeRequestProduct);

function makeRequestProduct() {
    let page = $('#page').val() - 1;
    let size = $('#size').val();
    let direction = $('#direction').val();
    let fieldName = $('#fieldName').val();
    let categoriesId = $('#categoriesId').val();
    $.ajax({
        url: mainUrl + "/product/page/filter?direction=" + direction + "&fieldName=" + fieldName + "&page=" + page + "&size=" + size + "&categoriesId=" + categoriesId,
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            appendProductsToContainer(res.data)
        },
        error: function (e) {
            console.log(e)
        }
    })
}
//
function appendProductsToContainer(products) {
    let $container = $('.container');
    $container.html('');
    for (let product of products) {
        let img = product.pathToImage ?
            mainUrl + '/img/' + product.pathToImage
            : 'http://denrakaev.com/wp-content/uploads/2015/03/no-image.png';
        $container.append(`
                    <div class="product">
                        <h3 class="product-title">
                            ${product.name}
                        </h3>
                        <img class="product-image" src="${img}">
                        <p class="product-category">Категорія: <strong>${product.categoryName}</strong></p>
                        <p class="product-name">Назва послуги: <strong>${product.name}</strong></p>
                        <p class="product-timeMinutes">Тривалість: <strong>${product.timeMinutes} хв</strong></p>
                        <p class="product-price">Ціна: <strong>₴${product.price}</strong></p>
                    </div>
                `)
    }
}

function makeRequestCategory() {
    $.ajax({
        url: mainUrl + "/category/page?direction=ASC&fieldName=name&page=0&size=100",
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            appendCategoriesToContainer(res.data)
        },
        error: function (e) {
            console.log(e)
        }
    })
}
//
function appendCategoriesToContainer(categories) {
    let $container = $('#categoriesId');
    $container.html('');
    for (let category of categories) {
        $container.append(`
                    <option value="${category.id}" selected>${category.name}</option>
                `)
    }
}

// function getPaginationRequest() {
//     return {
//         page: $('#page').val() - 1,
//         size: $('#size').val(),
//         sortRequest: {
//             field: 'name',
//             direction: $('#direction').val()
//         }
//     };
// }