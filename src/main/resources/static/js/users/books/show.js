document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('rating');
    let rating = 0;

    stars.forEach(star => {
        star.addEventListener('click', () => {
            rating = star.getAttribute('data-value');
            ratingInput.value = rating; // Set the hidden input value
            updateStars(rating);
        });
    });

    function updateStars(rating) {
        stars.forEach(star => {
            if (star.getAttribute('data-value') <= rating) {
                star.classList.add('selected');
            } else {
                star.classList.remove('selected');
            }
        });
    }

    // delete review
    const deleteModal = document.getElementById('deleteConfirmationModal');
    let reviewToDelete = null;

    window.showDeleteConfirmation = function(reviewId) {
        reviewToDelete = reviewId;
        deleteModal.style.display = 'block';
    }

    window.hideDeleteConfirmation = function() {
        reviewToDelete = null;
        deleteModal.style.display = 'none';
    }

    window.deleteReview = function() {
        if (reviewToDelete === null) return;

        const url = `/books/reviews/${reviewToDelete}`;
        console.log('Sending DELETE request to:', url);

        fetch(url, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                console.log('Review deleted successfully');
                location.reload(); // Reload the page to reflect the changes
            } else {
                console.error('Failed to delete review');
            }
        }).catch(error => {
            console.error('Error:', error);
        });

        hideDeleteConfirmation();
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const addToCartForm = document.getElementById('add-to-cart-form');
    const notification = document.getElementById('notification');

    addToCartForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Ngăn chặn hành động submit form ngay lập tức

        // Gửi form bằng JavaScript (AJAX) hoặc thực hiện hành động mà không reload trang.
        const formData = new FormData(addToCartForm);
        fetch(addToCartForm.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                // Hiển thị thông báo
                notification.style.display = 'block';
                setTimeout(() => {
                    notification.style.display = 'none';
                }, 3000);
            } else {
                console.error('Failed to add product to cart');
            }
        }).catch(error => {
            console.error('Error:', error);
        });
    });
});

