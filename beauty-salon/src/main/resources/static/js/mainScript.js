let mainUrl = "http://localhost:8090";
// let mainUrl = "http://localhost:8000";

makeRequestSectionsMain();

function makeRequestSectionsMain() {
    $.ajax({
        url: mainUrl + "/section/page?direction=ASC&fieldName=name&page=0&size=100",
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            appendSectionsToMenu(res.data);
            appendSectionsToContainer(res.data);
        },
        error: function (e) {
            console.log(e)
        }
    })
}

function appendSectionsToMenu(sections) {
    let $container = $('#sections');
    $container.html('');
    for (let section of sections) {

        $container.append(`
                    <li><a onclick="makeRequestCategoryMain(${section.id})">${section.name}</a></li>
                `)
    }
}
//
function appendSectionsToContainer(sections) {
    let $container = $('.container');
    $container.html('');
    for (let section of sections) {
        let img = section.pathToImage ?
            mainUrl + '/img/' + section.pathToImage
            : 'http://denrakaev.com/wp-content/uploads/2015/03/no-image.png';
        $container.append(`
                    <div class="product">
                        <a id="header-item" onclick="makeRequestCategoryMain(${section.id})"><h3 id="h3">${section.name}</h3><br>
                            <img class="product-image" src="${img}">
                        </a>
                    </div>
                `)
    }
}

function makeRequestCategoryMain(id) {
    $.ajax({
        url: mainUrl + "/category/page?direction=ASC&fieldName=name&page=0&size=100&sectionsId=" + id,
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            appendCategoriesToContainerMain(res.data)
        },
        error: function (e) {
            console.log(e)
        }
    })
}

function appendCategoriesToContainerMain(categories) {
    let $container = $('.container');
    $container.html('');
    for (let category of categories) {
        let img = category.pathToImage ?
            mainUrl + '/img/' + category.pathToImage
            : 'http://denrakaev.com/wp-content/uploads/2015/03/no-image.png';
        $container.append(`
                    <div class="product">
                        <a id="header-item" onclick="makeRequestProducts(${category.id})"><h3 id="h3">${category.name}</h3><br>
                            <img class="product-image" src="${img}">
                        </a>
                    </div>
                `)
    }
}

function makeRequestProducts(id) {
    $.ajax({
        url: mainUrl + "/product/page/filter?categoriesId=" + id + "&direction=ASC&fieldName=name&page=0&size=100",
        type: 'GET',
        contentType: 'application/json',
        success: function (res) {
            appendProductsToContainer(res.data);
        },
        error: function (e) {
            console.log(e)
        }
    })
}

function appendProductsToContainer(products) {
    let $container = $('.container');
    $container.html('');
    for (let product of products) {
        let img = product.pathToImage ?
            mainUrl + '/img/' + product.pathToImage
            : 'http://denrakaev.com/wp-content/uploads/2015/03/no-image.png';
        $container.append(`
                    <div class="product">
                        <a id="header-item"><h3 id="h3">${product.name}</h3><br>
                            <img class="product-image" src="${img}">
                        </a>
                        <p class="product-timeMinutes">Тривалість: <strong>${product.timeMinutes} хв</strong></p>
                        <p class="product-price">Ціна: <strong>₴${product.price}</strong></p>
                    </div>
                `)
    }
}