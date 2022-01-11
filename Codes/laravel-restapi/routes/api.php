<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\ProductController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// register user
Route::post('/register', [AuthController::class, 'register']);
// login user
Route::post('/login', [AuthController::class, 'login']);
    
// routing to a basic CRUD controller end points at once
// Route::resource('products', ProductController::class);

// route to get all products
Route::get('/products', [ProductController::class, 'index']);
// route to get product by id
Route::get('/products/{id}', [ProductController::class, 'show']);
// route for search product by name
Route::get('/products/search/{name}', [ProductController::class, 'search']);

// routes with sanctum authentication enabled
Route::group(['middleware' => 'auth:sanctum'], function() {

    Route::post('/products', [ProductController::class, 'store']);
    Route::put('/products/{id}', [ProductController::class, 'update']);
    Route::delete('/products/{id}', [ProductController::class, 'delete']);

    // logout user
    Route::post('/logout', [AuthController::class, 'logout']);
});

Route::middleware('auth:sanctum')->get('/user', function() {
   
});

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
