<?php
// src/Controller/ProductoController.php
namespace App\Controller;

//use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
//use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

// tipos form
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;

// clase
use App\Entity\Product;
// form
use App\Form\ProductType;


// Constraints
use Symfony\Component\Validator\Constraints\Currency;


/**
 * @Route("/producto")
 */
class ProductController extends AbstractController
{
    /**
     * @Route("/NewFormClase", name="NewFormClase")
     */
    public function claseNewForm( Request $request)
    {
       
        $producto = new Product();
        $form = $this->createForm(ProductType::class, $producto );
       
        $form->handleRequest( $request );

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($producto);
            $em->flush();
         
            return new Response( "Save");
        }
        else
            return $this->render('product/form.html.twig', array('form' => $form->createView(),));
    }
    
    
    /**
     * @Route("/EditFormClase/{id}", name="EditFormClase")
     */
     public function EditFormClase( Request $request, $id )
    {
       
        $producto = $this->getDoctrine()
        ->getRepository(Product::class)
        ->findOneById( $id );
		
        $form = $this->createForm(ProductType::class, $producto );
       
        $form->handleRequest( $request );

         if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($producto);
            $em->flush();
         
            return new Response( "Save");
        }
        else
            return $this->render('product/form.html.twig', array('form' => $form->createView(),));
    }
    
    
    /**
     * @Route("/EditFormSinClase/{id}", name="EditFormSinClase")
     */
     public function EditFormSinClase( Request $request, $id )
    {
       
        $producto = $this->getDoctrine()
        ->getRepository(Product::class)
        ->findOneById( $id );
         
       
         
        $defaultData = array('name' => $producto->getName(), 
                            'price' => $producto->getPrice(), 
                            'description' => $producto->getDescription(), 
                            'stock' => $producto->getStock(), );
      

        
        $form = $this->createFormBuilder($defaultData)
        ->add('name', TextType::class)
        // Validacion moneda
        ->add('price', MoneyType::class)
        ->add('description', TextType::class)
        ->add('stock', IntegerType::class)
        ->add('Send', SubmitType::class)
        ->getForm();
         
         
              
        $form->handleRequest( $request );

         if ($form->isSubmitted() && $form->isValid()) {
            // array con valores form field => value
            $data = $form->getData();
            
            $producto->setName($data[ 'name' ]);
            $producto->setPrice($data[ 'price' ]);
            $producto->setDescription($data[ 'description' ]);
            $producto->setStock($data[ 'stock' ]);
           
            $em = $this->getDoctrine()->getManager();
            $em->persist($producto);
            $em->flush();
         
            return new Response( print_r($data));
        }
        else
            return $this->render('product/form.html.twig', array('form' => $form->createView(),));
    }
    
    /**
     * @Route("/NewFormSinClase", name="NewFormSinClase")
     */
    public function NewFormSinClase( Request $request )
    {
       
      
         
         //var_dump( serialize( $producto ));
         //die();
        $defaultData = array();
         
        $form = $this->createFormBuilder($defaultData)
        ->add('name', TextType::class)
        // Validacion moneda
         ->add('price', MoneyType::class )
        ->add('description', TextType::class)
        ->add('stock', IntegerType::class)
        ->add('Send', SubmitType::class)
        ->getForm();
 
       
        $form->handleRequest( $request );

         if ($form->isSubmitted() && $form->isValid()) {
            // array con valores form field => value
            $data = $form->getData();
            
           
            $producto = new Product();
            $producto->setName($data[ 'name' ]);
            $producto->setPrice($data[ 'price' ]);
            $producto->setStock($data[ 'stock' ]);
            $producto->setDescription($data[ 'description' ]);
           
            $em = $this->getDoctrine()->getManager();
            $em->persist($producto);
            $em->flush();
         
            return new Response( "Save");
        }
        else
            return $this->render('product/form.html.twig', array('form' => $form->createView(),));
    }
    
    
    
}
