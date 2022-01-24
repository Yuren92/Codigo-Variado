<?php

namespace App\Controller;

use App\Entity\Articulo;
use App\Form\ArticuloType;
use App\Repository\ArticuloRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


use Symfony\Component\Form\Extension\Core\Type\SubmitType;



/**
 * @Route("/articulo")
 */
class ArticuloController extends AbstractController
{
    /**
     * @Route("/listar", name="articulo_listar")
     */
    public function listar()
    {
        
        $articulos = $this->getDoctrine()->getRepository(Articulo::class)->findAll();
        
        return $this->render('articulo/listar.html.twig', [
            'articulos' => $articulos
        ]);
    }

    /**
     * @Route("/new", name="articulo_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $articulo = new Articulo();
        $form = $this->createForm(ArticuloType::class, $articulo);
        
        $form->add('Aceptar', SubmitType::class );
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($articulo);
            $entityManager->flush();

            return $this->redirectToRoute('articulo_listar');
        }

        return $this->render('articulo/new.html.twig', [
            'articulo' => $articulo,
            'form' => $form->createView(),
        ]);
    }

   
    /**
     * @Route("/edit/{id}", name="articulo_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, $id )
    {
        
        $articulo = $this->getDoctrine()->getRepository(Articulo::class)->find($id);
        $form = $this->createForm(ArticuloType::class, $articulo);
        $form->add('Aceptar', SubmitType::class );
        $form->add('Borrar', SubmitType::class );
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            if( $form->get('Aceptar')->isClicked() ) // seleccion boton 
                return $this->redirectToRoute('articulo_listar');            
            else
                return $this->redirectToRoute('articulo_delete', [ 'id' => $id ] );
    
        }

        return $this->render('articulo/edit.html.twig', [
            'articulo' => $articulo,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/delete/{id}", name="articulo_delete")
     */
    public function delete( $id)
    {
        $articulo = $this->getDoctrine()->getRepository(Articulo::class)->find($id);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($articulo);
        $entityManager->flush();
        

        return $this->redirectToRoute('articulo_listar');
    }
}
