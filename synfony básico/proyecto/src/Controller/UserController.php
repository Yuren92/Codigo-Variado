<?php
namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;

class UserController extends AbstractController {

    // ... 

    public function notifications(): Response{
        $userFirstName = "Carlos";
        $userNotifications = ["Hola","Sisoy"];

        return $this->render('user/notifications.html.twig',[
            'userFirstName' => $userFirstName,
            'notifications' => $userNotifications,
        ]);
    }
}

?>