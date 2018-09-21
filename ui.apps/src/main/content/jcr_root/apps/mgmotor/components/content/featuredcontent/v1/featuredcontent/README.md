Suntrust Gloabl Featured Content (v1)
====
Featured Content Component that render different variations of the component based on Dialog Options and Style System.

## Features
* Multiple renditions via Style System
* Configurable color scheme for authoable fields

##Usage
* Featured Content Component must be used inside Featured Content Responsive Grid Component


### Use Object
* Sling Models : 
  * **com.suntrust.global.components.FeaturedContentComponent**
  	* *Exposed Methods*
      * List<FeaturedContentAdditionalLinks> getAdditionalLinks() - *Returns list of CTA Objects configured*
  * **com.suntrust.global.utils.ComponentStyleHelper**
    * *Exposed Methods*
        * List<String> getStyleClasses() - *get the list of styles added from Style System*
        * boolean isStyleIdValid() - *Returns true is the HTL value in styleIdParam is present in list of styels*


### Component Policy Configuration Properties
The following configuration properties are used:

1. To be Documented

### Edit Dialog Properties
The following properties are written to JCR for this List component and are expected to be available as `Resource` properties:

1. To de documented

## Client Libraries
The component provides a `suntrust.global.components.imagetext.v1.editor` editor client library category that includes JavaScript
handling for dialog interaction. It is already included by its edit dialog.

## BEM Description

	1. BLOCK cmp-list
    2. ELEMENT cmp-imagetext__text
    3. ELEMENT cmp-image__image
	4. MODIFIER cmp-imagetext--stylecenter
    5. MODIFIER cmp-imagetext--styleright
    6. MODIFIER cmp-imagetext--styleleft
`

## Information
* **Vendor**: Suntrust
* **Version**: v1
* **Compatibility**: AEM 6.3 SP2, AEM 6.4
* **Status**: production-ready

