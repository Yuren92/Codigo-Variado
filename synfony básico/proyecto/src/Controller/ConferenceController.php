<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use App\Entity\Product;
use Doctrine\Persistence\ManagerRegistry;

class ConferenceController extends AbstractController
{
    #[Route('/conference', name: 'conference')]

    public function index(): Response
    {
        return $this->render('conference/index.html.twig', [
            'controller_name' => 'ConferenceController',"msg"=>"hola mundo"
        ]);
    }

    #[Route('/listado', name: 'listado')]

    public function listado(): Response
    {
        $conferencias = ['php, esta muerto?','Angular vale para algo?', 'symfony te abrirar las puertas?'];

        return $this->render('conference/listado.html.twig', [
            'controller_name' => 'ConferenceController',"conferencias"=>$conferencias
        ]);
    }

    #[Route('/alta', name: 'alta')]

    public function alta(ManagerRegistry $doctrine): Response
    {
        $entityManager = $doctrine->getManager();

        $product = new Product();
        $product->setName('Pencil');
        $product->setPrice(25);

        // tell Doctrine you want to (eventually) save the Product (no queries yet)
        $entityManager->persist($product);

        // actually executes the queries (i.e. the INSERT query)
        $entityManager->flush(); //como un commit

        return $this->render('conference/msg.html.twig',['msg'=>'producto creado']);
    }


    #[Route('/select/{id}', name: 'select')] //pongo la id del producto, un parametro 

    public function select($id,ManagerRegistry $doctrine): Response
    {
        $product = $doctrine->getRepository(Product::class)->find($id); //buscar el Product con la id que pone en ->find()



        if (!$product) {
            return $this->render('productos/error.html.twig',['msg'=>'¡No existe el producto!']);
        }

        return $this->render('productos/producto.html.twig',['producto'=>$product]); //muestra el producto
    }


    #[Route('/borrar/{id}', name: 'borrar')] //pongo la id del producto, un parametro 

    public function borrar($id,ManagerRegistry $doctrine): Response
    {
        $product = $doctrine->getRepository(Product::class)->find($id); //buscar el Product con la id que pone en ->find()



        if (!$product) {
            return $this->render('productos/error.html.twig',['msg'=>'¡No existe el producto!']);
        }
        else{
            $entityManager = $doctrine->getManager();
            $entityManager->remove($product);
            $entityManager->flush();
            return $this->render('productos/borrar.html.twig',['borrado'=>'has borrado ','producto'=>$product]); //muestra el producto
        }
    }
    


    #[Route('/update/{id}/nombre/{nombre}/precio/{precio}', name: 'update')] //pongo la id del producto, un parametro 

    public function update($id,$precio,$nombre,ManagerRegistry $doctrine): Response
    {
        $product = $doctrine->getRepository(Product::class)->find($id); //buscar el Product con la id que pone en ->find()



        if (!$product) {
            return $this->render('productos/error.html.twig',['msg'=>'¡No existe el producto!']);
        }
        else{
            $product->setName($nombre);
            $product->setPrice($precio);
            $entityManager = $doctrine->getManager();
            $entityManager->persist($product);
            $entityManager->flush();
            return $this->render('productos/update.html.twig',['update'=>'has actualizado ','producto'=>$product]); //muestra el producto
        }
    }

}
