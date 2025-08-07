
function deleteTeacher(endpoint, id, token) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(endpoint + id, {
            method: "delete",
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(res => {
            console.log(res.status)
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else
                alert("Có lỗi xảy ra!");
        });
    }
}

function updateRoleTeacher(endpoint, id, token){
    fetch(endpoint + id, {
        method: "post",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        if (res.status === 200) {
            alert("Nâng cấp thành công")
            location.reload();
        } else
            alert("Có lỗi xảy ra!");
    });
}

function downRoleTeacher(endpoint, id, token){
    fetch(endpoint + id, {
        method: "post",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        if (res.status === 200) {
            alert("Hạ role thành công")
            location.reload();
        } else
            alert("Có lỗi xảy ra!");
    });
}


function deleteSubject(endpoint, id, token){
    fetch(endpoint + id, {
        method: "post",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        if (res.status === 204) {
            alert("Hạ role thành công")
            location.reload();
        } else
            alert("Có lỗi xảy ra!");
    });
}