function showBestSellers() {
    document.getElementById('bestSellersSection').style.display = 'flex';
    document.getElementById('topRatedSection').style.display = 'none';
    document.getElementById('bestSellersTab').classList.add('filter-active');
    document.getElementById('topRatedTab').classList.remove('filter-active');
}

function showTopRated() {
    document.getElementById('topRatedSection').style.display = 'flex';
    document.getElementById('bestSellersSection').style.display = 'none';
    document.getElementById('topRatedTab').classList.add('filter-active');
    document.getElementById('bestSellersTab').classList.remove('filter-active');
}