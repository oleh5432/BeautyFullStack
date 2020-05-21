let mainUrl = "http://localhost:8080";
    // let mainUrl = "http://localhost:8000";

    getAllCreatedCategories();
    setModalImgConfiguration();
    setModalUpdateConfiguration();
    setActionOnCreateCategoryBtn();
    setActionOnUpdateButton();
    makeRequestSection();

    // start when load page PS reload page for triggered http request
    function getAllCreatedCategories() {
    $.ajax({
        url: mainUrl + "/category/page?direction=ASC&fieldName=sectionName&page=0&size=100",
        type: "GET",
        contentType: "application/json",
        success: function (dataResponse) {
            setCreatedCategoriesToTable(dataResponse.data);
            setActionOnDeleteButtonsCategory();
            setActionOnImgCategoriesButtons();
            setActionOnUpdateCategoryButtons();
        },
        error: function (error) {
            console.log(error.message);
        }
    });
}

    function setCreatedCategoriesToTable(categories) {
    for (var i = 0; i < categories.length; i++) {
    setCreatedCategoryToTable(categories[i]);
}
}

    function setCreatedCategoryToTable(category) {
    var tableOfCategories = $("#allElements");
    tableOfCategories.append('<tr>' +
    '<td>' + category.id + '</td>' +
    '<td>' + category.name + '</td>' +
    '<td>' + category.sectionName + '</td>' +
    '<td>' +
    // '<a href="' + mainUrl + '/img/' + category.pathToImage + '" target="_blank">' +
    '<button class="img-button" value="' + category.id + '">' +
    'Переглянути зображення' +
    '</button>' +
    // '</a>' +
    '</td>' +
    '<td><button class="button" id="buttonDeleteCategory" value="' + category.id + '">Видалити</button></td>' +
    '<td><button class="buttonUpdate" value="' + category.id + '">Оновити</button></td>' +
    '</tr>');
}

    //delete process
    function setActionOnDeleteButtonsCategory() {
    $(".button").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/category?id=" + $(this).val(),
                type: "DELETE",
                success: function (data) {
                    location.reload();
                },
                error: function (error) {
                    console.log(error);
                }
            });
        })
    })

}

    function setActionOnCreateCategoryBtn() {
    $("#buttonCreateCategory").click(function(){
        let file = document.getElementById("getFileCategory").files[0];
        getBase64(file).then(data => {

            let category = {
                "fileRequest": {
                    "fileName": $("#nameCategory").val(),
                    "data": data
                },
                "name": $("#nameCategory").val(),
                "sectionId": $("#sectionId").val()
            };

            $.ajax({
                url: mainUrl + "/category",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(category),
                success: function (data) {
                    console.log(data);
                    location.reload();
                },
                error: function (error) {
                    alert(error);
                    console.log(error);
                }
            });
        });
    });
}

    //update process
    function setActionOnUpdateButton() {
    $("#btnUpdateButton").click(function(){
        let file = document.getElementById("getFileUpdate").files[0];
        getBase64(file).then(data => {

            let category = {
                "categoryId": $("#categoryId").val(),
                "sectionId": $("#sectionsIdUpdate").val(),
                "fileRequest": {
                    "fileName": $("#nameUpdate").val(),
                    "data": data
                },
                "name": $("#nameUpdate").val()
            };

            $.ajax({
                url: mainUrl + "/category?id=" + $("#categoryId").val(),
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(category),
                success: function (data) {
                    console.log(data);
                    location.reload();
                    alert("Категорію оновлено");
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
    });
}


    function getCategoryById(id) {
    // let newCategory = {};
    $.ajax({
        url: mainUrl + "/category/findById?id=" + id,
        type: "GET",
        contentType: "application/json",
        success: function (category) {
            // newCategory = category;
            $("#nameUpdate").val(category.name);
            $('#sectionsIdUpdate option[name="'+ category.sectionName + '"]').prop('selected', true);
        },
        error: function (error) {
            console.log(error);
        }
    });
    // return newCategory;
}

    // function setInputValueForUpdateCategory(category) {
    //     $("#nameUpdate").val(category.name);
    // }

    function setActionOnUpdateCategoryButtons() {
        $(".buttonUpdate").each(function (index) {
            $(this).click(function () {
                $('#categoryId').val($(this).val());
                // дописати функцію яка повертає один продукт за id, щоб можна було відразу підставити при кліку в інпути старі значення
                // setInputValueForUpdateCategory(getCategoryById($(this).val()));
                getCategoryById($(this).val());
                document.getElementById('modalUpdate').style.display = "block";
            })
        })
    }

    function getBase64(file) {
    return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});
}

    function setActionOnImgCategoriesButtons() {
    $(".img-button").each(function (index) {
    $(this).click(function () {
    getImgCategory($(this).val());
})
})
}

    function getImgCategory(id) {
    $('#img-container').html('');
    $.ajax({
    url: mainUrl + "/category/findById?id=" + id,
    type: "GET",
    contentType: "application/json",
    success: function (category) {
    $('#img-container').append('<img class="category-image" src="' + mainUrl + '/img/' + category.pathToImage + '">')
    document.getElementById('imgModal').style.display = "block";
},
    error: function (error) {
    console.log(error);
}
});
}

    function setModalImgConfiguration() {
    // Get the modal
    var modal = document.getElementById('imgModal');

    // Get the <span> element that closes the modal
    var span = document.getElementById('closeImgModal');

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
    modal.style.display = "none";
};

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
    if (event.target == modal) {
    modal.style.display = "none";
}
};
}

    //конфіг для модального вікна оновлення
    function setModalUpdateConfiguration() {
    // Get the modal
    var modal = document.getElementById('modalUpdate');

    // Get the <span> element that closes the modal
    var span = document.getElementById('closeModalUpdate');

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
    modal.style.display = "none";
};

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
    if (event.target == modal) {
    modal.style.display = "none";
}
};
}

    function makeRequestSection() {
    $.ajax({
    url: mainUrl + "/section/page?direction=ASC&fieldName=name&page=0&size=100",
    type: 'GET',
    contentType: 'application/json',
    success: function (res) {
    appendSectionsToMenu(res.data);
    appendSectionsToContainer(res.data);
    appendSectionsToContainerUpdate(res.data);
},
    error: function (e) {
    console.log(e)
}
})
}
    //
    function appendSectionsToContainer(sections) {
    let $container = $('#sectionsId');
    $container.html('');
    for (let section of sections) {
    $container.append(`
                    <option value="${section.id}">${section.name}</option>
                `)
}
}

    function appendSectionsToContainerUpdate(sections) {
    let $container = $('#sectionsIdUpdate');
    $container.html('');
    for (let section of sections) {
    $container.append(`
                    <option name="${section.name}" value="${section.id}">${section.name}</option>
                `)
}
}

    function appendSectionsToMenu(sections) {
    let $container = $('#sections');
    $container.html('');
    for (let section of sections) {

    $container.append(`
                    <li><a onclick="makeRequestCategory(${section.id})">${section.name}</a></li>
                `)
}
}