<?php

namespace App\Http\Controllers;

use App\Events\MessageCreated;
use App\Events\MessageSent;
use Illuminate\Http\Request;

class MessagesController extends Controller
{
    protected function index() 
    {
        return view('messages');
    }

    protected function sendMessage(Request $request)
    {
        $message = $request->message;
        broadcast(new MessageSent($message));
        return response()->json([
            'status' => 'message is sent successfuly!'
        ]);
    }
}
