<!DOCTYPE html>
<html lang="zxx">
<head>
    <title>Listto - Directory Listing HTML Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">

   
</head>
<body>
<div class="page_loader"></div>


<!-- Banner start -->
<div class="banner banner_video_bg" id="banner">
    <div class="pattern-overlay">
        <a id="bgndVideo" class="player" data-property="{videoURL:'https://www.youtube.com/watch?v=5e0LxrLSzok',containment:'.banner_video_bg', quality:'large', autoPlay:true, mute:true, opacity:1}">bg</a>
    </div>
    <div id="bannerCarousole" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item banner-max-height active">
                <div class="carousel-caption banner-slider-inner"></div>
            </div>
        </div>
    </div>
    <div class="bi-4 bi-6">
        <div class="container">
            <div class="text-c">
                <h1 class="b-text">What's Your Plan Today?</h1>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit</p>
                <div class="inline-search-area ml-auto mr-auto">
                    <div class="search-boxs">
                        <div class="search-col">
                            <input type="text" name="search" class="form-control has-icon b-radius" placeholder="What are you looking for?">
                        </div>
                        <div class="search-col">
                            <i class="fa fa-map-marker icon-append"></i>
                            <input type="text" name="location" class="form-control has-icon b-radius" placeholder="Location">
                        </div>
                        <div class="search-col categories b-radius">
                            <select class="selectpicker search-fields" name="location">
                                <option>All Categories</option>
                                <option>Shops</option>
                                <option>Hotels</option>
                                <option>Restaurants</option>
                                <option>Fitness</option>
                            </select>
                        </div>
                        <div class="find">
                            <button class="btn button-theme btn-search btn-block b-radius">
                                <i class="fa fa-search"></i><strong>Find</strong>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Banner end -->

<!-- Categories strat -->
<div class="categories content-area-15">
    <div class="container">
        <!-- Main title -->
        <div class="main-title text-center">
            <h1>Most Popular Categories</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
        <!-- Slick slider area start -->
        <div class="slick-slider-area">
            <div class="row slick-carousel" data-slick='{"slidesToShow": 3, "responsive":[{"breakpoint": 1024,"settings":{"slidesToShow": 2}}, {"breakpoint": 768,"settings":{"slidesToShow": 1}}]}'>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-1-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="list-sidebar.jsp">Shops</a>
                                    </h3>
                                    <h4 class="category-subtitle">45 Listings</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-supermarket"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-5-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="#">Restaurant</a>
                                    </h3>
                                    <h4 class="category-subtitle">98 Listing</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-cook"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-4-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="list-sidebar.jsp">Event</a>
                                    </h3>
                                    <h4 class="category-subtitle">45 Listings</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-calendar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-6-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="#">Nightlife</a>
                                    </h3>
                                    <h4 class="category-subtitle">85 Listings</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-guitar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-2-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="#">Hotels</a>
                                    </h3>
                                    <h4 class="category-subtitle">27 Listings</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-hotel"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="category">
                        <div class="category_bg_box cat-3-bg">
                            <div class="category-overlay">
                                <div class="category-content">
                                    <h3 class="category-title">
                                        <a href="#">Gym</a>
                                    </h3>
                                    <h4 class="category-subtitle">85 Listings</h4>
                                </div>
                                <div class="icon">
                                    <i class="flaticon-gym"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="slick-btn">
                <div class="slick-prev slick-arrow-buton-2"></div>
                <div class="slick-next slick-arrow-buton-2"></div>
            </div>
        </div>
    </div>
</div>
<!-- Categories end -->

<!-- Listing item start -->
<div class="listing-item content-area-16 bg-grea-3">
    <div class="container">
        <!-- Main title -->
        <div class="main-title">
            <h1>Most Visited Places</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
        <!-- Slick slider area start -->
        <div class="slick-slider-area">
            <div class="row slick-carousel" data-slick='{"slidesToShow": 3, "responsive":[{"breakpoint": 1024,"settings":{"slidesToShow": 2}}, {"breakpoint": 768,"settings":{"slidesToShow": 1}}]}'>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open open-color">Now Open</div>
                                <div class="tag">Gym</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">Gym In Fitnees</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open new-color">New</div>
                                <div class="tag">Event</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">My Parsonal Event</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open featured-color">Featured</div>
                                <div class="tag">Restourant</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">The Green Restourant</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open featured-color">Featured</div>
                                <div class="tag">Hotels</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">The Hotel Alpha</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open new-color">New</div>
                                <div class="tag">Eat & Drink</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">Best Place For Drink</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="slick-slide-item">
                    <div class="listing-item-box">
                        <div class="listing-thumbnail">
                            <div class="listing-photo">
                                <div class="now-open featured-color">Featured</div>
                                <div class="tag">Shops</div>
                                <a class="icon">
                                    <i class="flaticon-heart"></i>
                                </a>
                                <img class="d-block w-100" src="http://placehold.it/350x247" alt="listing-photo">
                            </div>
                        </div>
                        <div class="detail">
                            <h3 class="title">
                                <a href="listing-single.jsp">Shop In City</a>
                            </h3>
                            <ul>
                                <li><i class="flaticon-pin"></i>123 Kathal St. Tampa City,</li>
                                <li><i class="flaticon-phone"></i>+0477 85X6 552</li>
                                <li><i class="flaticon-mail"></i>info@themevessel.com</li>
                            </ul>
                        </div>
                        <div class="footer">
                            <div class="ratings">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star-o"></i>
                                <span>(327 Reviews)</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="slick-prev slick-arrow-buton">
                <i class="fa fa-angle-left"></i>
            </div>
            <div class="slick-next slick-arrow-buton">
                <i class="fa fa-angle-right"></i>
            </div>
        </div>
    </div>
</div>
<!-- Listing item end -->

<!-- Counters strat -->
<div class="counters">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="media counter-box">
                    <div class="icon">
                        <i class="flaticon-award"></i>
                    </div>
                    <div class="media-body align-self-center">
                        <h2 class="counter">967</h2>
                        <p>Awards Winning</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="media counter-box">
                    <div class="icon">
                        <i class="flaticon-calendar"></i>
                    </div>
                    <div class="media-body align-self-center">
                        <h2 class="counter">177</h2>
                        <p>Done Projects</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="media counter-box">
                    <div class="icon">
                        <i class="flaticon-user"></i>
                    </div>
                    <div class="media-body align-self-center">
                        <h2 class="counter">1276</h2>
                        <p>Happy Clients</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="media counter-box">
                    <div class="icon">
                        <i class="flaticon-supermarket-1"></i>
                    </div>
                    <div class="media-body align-self-center">
                        <h2 class="counter">396</h2>
                        <p>Cups Of Cofee</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Counters end -->

<!-- Services start -->
<div class="services content-area ">
    <div class="container">
        <!-- Main title -->
        <div class="main-title text-center">
            <h1>How It Works</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
        <div class="row">
            <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                <div class="service-info">
                    <div class="icon">
                        <i class="flaticon-find"></i>
                    </div>
                    <h3>Find Interesting Place</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt</p>
                </div>
            </div>
            <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6">
                <div class="service-info">
                    <div class="icon">
                        <i class="flaticon-mail"></i>
                    </div>
                    <h3>Contact a Few Owners</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt</p>
                </div>
            </div>
            <div class="col-xl-4 col-lg-4 col-md-6 col-sm-6 none-992">
                <div class="service-info">
                    <div class="icon">
                        <i class="flaticon-user"></i>
                    </div>
                    <h3>Make a Reservation</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Services end -->

<!-- Testimonial start -->
<div class="testimonial">
    <div class="container">
        <div class="row">
            <div class="offset-lg-2 col-lg-8">
                <div class="testimonial-inner">
                    <header class="testimonia-header">
                        <h1>Testimonial</h1>
                    </header>
                    <div id="carouselExampleIndicators3" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators3" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselExampleIndicators3" data-slide-to="1" class=""></li>
                            <li data-target="#carouselExampleIndicators3" data-slide-to="2" class=""></li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <div class="testimonial-info">
                                    <div class="sz">
                                        <p class="lead">
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in pulvinar neque. Nulla finibus lobortis pulvinar. Donec a consectetur nulla. Nulla posuere sapien vitae.
                                        </p>
                                    </div>
                                    <div class="media mb-4">
                                        <a class="pr-3" href="user-profile.jsp">
                                            <img src="http://placehold.it/60x60" alt="team-3" class="img-fluid">
                                        </a>
                                        <div class="media-body align-self-center">
                                            <h5>
                                                <a href="user-profile.jsp">Anne Brady</a>
                                            </h5>
                                            <h6>Restaurant Owner</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="testimonial-info">
                                    <div class="sz">
                                        <p class="lead">
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in pulvinar neque. Nulla finibus lobortis pulvinar. Donec a consectetur nulla. Nulla posuere sapien vitae.
                                        </p>
                                    </div>
                                    <div class="media mb-4">
                                        <a class="pr-3" href="user-profile.jsp">
                                            <img src="http://placehold.it/60x60" alt="team-3" class="img-fluid">
                                        </a>
                                        <div class="media-body align-self-center">
                                            <h5>
                                                <a href="user-profile.jsp">Antony Moore</a>
                                            </h5>
                                            <h6>Restaurant Owner</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="testimonial-info">
                                    <div class="sz">
                                        <p class="lead">
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in pulvinar neque. Nulla finibus lobortis pulvinar. Donec a consectetur nulla. Nulla posuere sapien vitae.
                                        </p>
                                    </div>
                                    <div class="media mb-4">
                                        <a class="pr-3" href="user-profile.jsp">
                                            <img src="http://placehold.it/60x60" alt="team-3" class="img-fluid">
                                        </a>
                                        <div class="media-body align-self-center">
                                            <h5>
                                                <a href="user-profile.jsp">Emma Connor</a>
                                            </h5>
                                            <h6>Restaurant Owner</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Testimonial end -->

<!-- Blog start -->
<div class="blog content-area">
    <div class="container">
        <!-- Main title -->
        <div class="main-title">
            <h1>Latest Blog</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </div>
        <div class="row">
            <div class="col-lg-4 col-md-6">
                <div class="blog-3">
                    <div class="blog-photo">
                        <img src="http://placehold.it/350x233" alt="blog" class="img-fluid">
                        <div class="date-box">
                            <span>23</span>Feb
                        </div>
                    </div>
                    <div class="detail">
                        <h3>
                            <a href="blog-details.jsp">Starting your fitnees</a>
                        </h3>
                        <div class="post-meta">
                            <span><a href="#"><i class="flaticon-user"></i>Amdin</a></span>
                            <span><a href="#"><i class="flaticon-comment"></i>27</a></span>
                            <span><a href="#"><i class="fa fa-heart-o"></i>27</a></span>
                        </div>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <div class="blog-3">
                    <div class="blog-photo">
                        <img src="http://placehold.it/350x233" alt="blog" class="img-fluid">
                        <div class="date-box">
                            <span>27</span>Feb
                        </div>
                    </div>
                    <div class="detail">
                        <h3>
                            <a href="blog-details.jsp">top 35 Hotel in canada</a>
                        </h3>
                        <div class="post-meta">
                            <span><a href="#"><i class="flaticon-user"></i>Amdin</a></span>
                            <span><a href="#"><i class="flaticon-comment"></i>27</a></span>
                            <span><a href="#"><i class="fa fa-heart-o"></i>27</a></span>
                        </div>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 none-992">
                <div class="blog-3">
                    <div class="blog-photo">
                        <img src="http://placehold.it/350x233" alt="blog" class="img-fluid">
                        <div class="date-box">
                            <span>27</span>Feb
                        </div>
                    </div>
                    <div class="detail">
                        <h3>
                            <a href="blog-details.jsp">The best food in dubai</a>
                        </h3>
                        <div class="post-meta">
                            <span><a href="#"><i class="flaticon-user"></i>Amdin</a></span>
                            <span><a href="#"><i class="flaticon-comment"></i>27</a></span>
                            <span><a href="#"><i class="fa fa-heart-o"></i>27</a></span>
                        </div>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Blog end -->


<!-- Full Page Search -->
<div id="full-page-search">
    <button type="button" class="close">×</button>
    <form action="index.jsp#">
        <input type="search" value="" placeholder="type keyword(s) here" />
        <button type="submit" class="btn btn-sm button-theme">Search</button>
    </form>
</div>


</body>
</html>