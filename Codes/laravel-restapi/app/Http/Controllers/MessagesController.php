<?php

namespace App\Http\Controllers;

use App\Events\MessageCreated;
use App\Events\MessageSent;
use App\Models\Message;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class MessagesController extends Controller
{
    protected function index() 
    {
        return view('messages');
    }

    protected function sendMessage(Request $request)
    {

        // do not allow empty messages
        $request->validate([
            'content' => 'required'
        ]);

        $author = "anonymous";
        $content = $request->content;

        // check if author exists in request
        if($request->has('author')) $author = $request->author;

        $message = new Message([
            'author' => $author,
            'content' => $content
        ]);

        $message->save();

        // send broadcast for realtime functionality
        broadcast(new MessageSent($message));

        Log::info("sending: ". $message->toJson(JSON_PRETTY_PRINT));

        return response()->json([
            'status' => 'message is sent successfuly!'
        ]);
    }
}
