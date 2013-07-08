/****************************************************************************/
/* HRRJOU																	*/
/*	Date	  Action  User	Commentaires									*/
/*	11/06/04  Modif	  DG	- ajout de la notion de date DATCALF  			*/
/*							- maj des info dans HOPRAPP 					*/
/*	14/06/04  Modif	  NL	recuperation de LIBCOURT au lieu de 			*/
/*							LIBELLE pour la section horaire					*/
/*  07/07/04  Modif   DG	passage de paramères pour HRCPLA				*/
/*	10/08/04  Modif	  DG	comparaison de VALMIN avec num2_LV au 			*/
/*							lieu de num2_HV									*/
/*	11/08/04  Modif	  DG	Mise clé primaire ds Select for update			*/
/*	03/09/04  Modif	  DG	Changement condition pour INMISS				*/
/*	20/09/04  Modif	  DG	-Pas de blocage si DATCALC >= DATJOUR			*/
/*							-Durée à blanc interdite si valoris H ou N		*/
/*	27/09/04  Modif	  DG	Recalc=0 si 1er mvt inséré						*/
/*	17/11/04  Modif	  DG	Modif analytique								*/
/*	03/12/04  Modif	  DG	Remplacement du db_update du recalcul			*/
/*							par un db_exec("update"...						*/
/*	07/12/04  Modif	  DG	Gestion champ hopmoti.ANALYT					*/
/*							ajout modularité								*/
/*	13/12/04  Modif	  DG	Correction bug #1653							*/
/*							correctionpb avec erreur n°31					*/
/*	14/12/04  Modif	  DG	chronologie des activités						*/
/*	15/12/04  Modif	  DG	Suppr indspe GestIndexCDC						*/
/*	16/12/04  Modif	  DG	Gestion des ano analytique						*/
/*	17/12/04  Modif   NP    Update_ActivitePoste mise en commenatire des    */
/*                                test if (actiTag == 1)                    */
/*	17/12/04  Modif   NP  	if (actiTag == 1) ne doit pas etre en commentaire */
/*	22/12/04  Modif	  DG	Correction Bug #1722 : contrôle chgt			*/
/*								du motif badgé								*/
/*	23/12/04  Modif	  DG	Correction Bug #1749 (durée abs)				*/
/*	07/12/05  Modif	  DG	Mes non trouvé qd cdc inexistant				*/
/*	11/01/05  Modif	  DG	Modif LECPOIN(suppr ref T_REPA)					*/
/*	31/01/05  Modif	  DG	Changement message (REP->JOU)					*/
/*	03/02/05  Modif	  DG	Bug #2290 gestion tps négatif (ANA)				*/
/*	07/02/05  Modif	  DG	Bug #2358 gestion imp. manuelle					*/
/*	10/02/05  Modif	  DG	Correction saisie des absences					*/
/*  04/03/05  Modif   FA	Gestion des absences dans PL					*/
/*										KER_Update_ActivitePoste			*/
/*	04/03/05  Modif	  DG	Bug #2646										*/
/*	07/03/05  Modif	  DG	Bug #2358: gestion inmod = 2					*/
/*  21/03/05  Modif   DG    Eclatement des tables hopprof et hopattr		*/
/*  31/03/05  Modif   DG    Bug #2358: inmod=2 pour absences        		*/
/*	08/04/05  Modif	  DG	Bug #2973: correction pb ordre					*/
/*	04/05/05  Modif	  DG	Bug #3068: correction chgt typeve				*/
/*							Bug #2588: correction heure début profil		*/
/*	06/06/05  Modif	  JYC	Test de PLAGEDYN pour les plages dynamiques     */
/*	30/06/05  Modif	  NL	Utilisation routine MessErrAccesMatri			*/
/* 18/07/2005 Modif   NL	modif parametre MessErrAccesMatri				*/
/*	27/07/05  Modif	  DG	Bug #3769: affich hradeb & hrafin qd inmod=2	*/
/*	23/09/05  Modif	  DG	BT4335: changement affichage valoris nombre		*/
/*							BT4181: optimisation pour analytique			*/
/*	27/09/05  Modif	  DG	BT4287: correction pointages égaux				*/
/*	05/10/05  Modif	  DG	BT4470 & 4471 correction pb conversion des nbs	*/
/*	26/10/05  Modif	  NL	utilisation routine Rech_DroitFonction			*/
/*	07/11/05  Modif	  NL	BT 4763 4761 - nom des fichiers de travail		*/
/*							distinct pour l'AS400							*/
/*	21/11/05  Modif	  DG	BT4905: correction index des heures analytique	*/
/*	24/11/05  Modif	  DG	BT4826: non modification des motifs analytiques	*/
/*	05/01/06  Modif	  DG	BT452: gestion des valorisations suivant motif	*/
/*	19/01/06  Modif	  DG	BT5248: correction gestion des indices pour tab	*/
/*								'Activités' (analytique)					*/
/*	25/01/06  Modif	  DG	BT5244 Correction lecture du profil pour plages	*/
/*								dynamiques									*/
/*	15/02/06  Modif   DG	BT5554: plus de plantage si sites incohérents	*/
/*	07/03/06  Modif   DG	Correction protection bouton calcul				*/
/*	24/03/06  Modif	  DG	BT5850: retour en arrière BT452					*/
/*							BT5959: ajout d'un message d'erreur si le nb de	*/
/*								motif saisi est > à MAX_ABSENCE				*/
/*	27/03/06  Modif	  DG	BT5820: correction suppression du motif mission	*/
/*	12/04/06  Modif	  DG	BT4254: si l'utilisateur est déclarant, blocage	*/
/*								du matri									*/
/*  24/04/06  Modif   FA    BT6318 Pb sur appel de KER_Update_ActivitePoste */
/*							on ne fait plus l'appel sur le delete mais dès	*/
/*							qu'une absence a changé							*/
/* 11/05/06	 Modif    AA	BT 6406 Plantage du à correction du BT 6318		*/
/*						    Cause : Il y a des cas où HABS n'est pas renseigné*/
/*							-> Ajout test si au moins une absence supprimee	*/
/*							  Retour au test if ActiTag == 1				*/
/*	28/06/06  Modif	  DG	BT5643: gestion heures début et fin et durées	*/
/*	10/07/06  Modif	  DG	BT6834: correction formatage consignes			*/
/*  20/06/2006Modif	ER  Ajout de MAIL_ANOMALIE  						*/
/*	24/07/06  Modif	  DG	BT6954: protection hres deb et fin si la consigne*/
/*								est en nombre								*/
/*	17/08/06  Modif	  DG	BT4194: ajout de CTRL_AccesHisto pour limitation*/
/*								en consultation								*/
/*	04/09/06  Modif	  DG	BT5555: changement T_Tmem						*/
/*	27/09/06  Modif	  DG	BT7478: correction lecture atpeven pour analytic*/
/*	06/10/06  Modif	  DG	BT6826: correction quand ajout+modif d'un mvt	*/
/*	09/10/06  Modif	  DG	BT7732: correction suppression badgeage réel	*/
/*	10/10/06  Modif	  DG	gestion protection du bouton calcul				*/
/*	23/10/06  Modif	  DG	BT7926: correction suppression mouvements		*/
/*  09/11/06  Modif   ER    BT7808 - pb gestion MAIL_ANOM					*/
/* 11/12/06   Modif     FA      BT6814 Appel de KER_PL_UpdatePosteProfil 	*/
/*								pour gestion pose profil repos				*/
/*	12/12/06  Modif	  DG	BT8399: correction index tableau				*/
/*	07/03/07  Modif	  DG	Ajout paramètre dans AbsDisplayManagement()		*/
/*	26/03/07  Modif	  DG	BT9520: pas d'affichage de l'onglet activités si*/
/*								pas de module analytique					*/
/*	31/05/07  Modif	  DG	BT1602: protection en lecture si PS.READONLY = '1'*/
/*	15/06/07  Modif   DG	BT10265: passage du rang sur 3 caractères		*/
/*	20/06/07  Modif   DG	BT10264: Ajout d'un zoom vers HIN				*/
/*	07/08/07  Modif	  DG	BT10085: Recalcul durée si ctrl cohérence		*/
/*	10/10/07  Modif	  DG	BT5796: Test profil & code horaire inactif		*/
/*	11/10/07  Modif	  DG	BT11098: init du libellé de l'attribution		*/
/*  24/10/07  modif   SB    BT11096 pour la fonction JOU, autoriser l'affichage
                            des données du domaine EMPL                     */
/*  08/01/08  Modif   SG    BT10746 : Suppression du contrôle sur l'ordre   */
/*                          chronologique de saisie des pointages : l'ajout */
/*                          doit se faire sur une ligne libre, l'enreg. des */
/*                          modifications apportées retrie chronologiquement*/
/*                          les mouvements                                  */
/*	07/02/08  Modif	  DG	BT12218: changement test lecture motif mission	*/
/*	25/03/08  Modif	  JYC	BT10746: Numérotation des badgeages               */
/*	25/03/08  Modif	  JYC	BT2954: Gestion des dates de modification dans    */
/*                        hophabs et hoppoin                                */
/*  26/03/08  modif   SB    BT11598 : ajout du bouton CAF   et gérer l'appel à la modale CAF */
/*	31/03/08  Modif	  DG	Inversion des PERaff1()							*/
/*	07/04/08  Modif	  DG	BT11310: pas de mise à blanc des durées si abs	*/
/*								en heure									*/
/*  25/04/08  Modif   ER    BT13156 Mail incomplet, plusieurs process écrivent dans le même fichier*/
/*  18/06/08  Modif   MAB   BT5655 evolution de MAIL_ANOMALIE (txt de anomalie)*/
/*  19/06/08  Modif   MAB   BT13666 ajout de HRSPAS dans MAIL_ANOMALIE pour */
/*                          tenir compte de la langue du destinataire       */
/*	21/08/08  Modif	  DG	BT11310: correction mise à blanc des durées		*/
/*	25/08/08  Modif	  JYC	BT2954: Correction de la gestion des dates de */
/*                        modification dans hophabs                     */
/*  01/09/08  Modif   MAB   BT5655 MAIL_ANOMALIE ajout des autres anomalies */
/*	30/09/08  Modif	  AS	BT11310: correction mise à blanc des durées		*/
/*  09/09/08  Modif   MAB   BT5655 correction MAIL_ANOMALIE pour fichier joint*/
/*	13/11/08  Modif	  DG	BT15125: correction pb heures pour consigne de type N*/
/*	21/01/09  Modif	  DG	BT15661: gestion Rech_DroitFonction() pour onglet analytique */
/*	23/04/09  Modif	  DG	BT15860: gestion des motifs inactifs			*/
/*	20/08/09  Modif   DG	BT17536: externalisation des fonctions pour DIJ	*/
/*	25/08/09  Modif	  DG	Changement update memo							*/
/*	02/09/09  Modif	  SG	BT7121 - Lors de l'appel à la madale HR/CMV, mi-*/
/*                          se à blanc de la période de dates afin que l'ap-*/
/*                          parence soit celle de la gestion collective     */
/*	06/10/09  Modif		JD	    BT16483 règle RH non respectée              */
/*	11/02/10  Modif	  DG	BT19726: correction analytique					*/
/*	08/04/10  Modif	  AS	BT0 centralisation des accès à la table HJOU    */
/*  29/04/10  modif   SB    BT0 implémentation du cmphjou_reset dans le dao_prepare */
/*	05/05/10  Modif	  DG	BT20898: Chgt gestion pour type déclarant en zoom*/
/*  27/05/10  modif   SB    BT0 : MAIL_ANOMALIE déplacé dans hrsjou
                            gestion des anomalies sur table hophano         */
/*	14/06/10  Modif	  DG	Modif Libellé onglets Mvt/Abs/Cns				*/
/*	14/10/10  Modif   SG	BT22188 - En départ vers modale HR/CMV, suppri- */
/*							mer la mise à blanc des dates de début et de fin*/
/*	10/11/10  Modif	  DG	BT22502: affichage du libellé long pour le profil*/
/*	20/01/11  Modif	  DG	Correction controle analytique					*/
/*	16/02/12  Modif	  SG	BT27426 - Ajout du bouton d'accès à la modale   */
/*                          de changement de section horaire (HR/CSH)       */
/*	05/04/12  Modif	  DG	BT27592 : Ajout accès aux modales CHO et CPR	*/
/*	25/04/12  Modif	  DG	BT15721 : Gestion des listes de sites			*/
/*  24/10/12  Ano     CP    BT32559 Ne pas tester le respect des contraintes */
/*                          planification pour les consignes				 */
/*  12/11/12  Ano     CP    BT32559 appel de KER_Test_Contrainte_PrecSuiv	*/
/*                          après le calculateur							*/
/****************************************************************************/
#include <string.h>
#ifndef HQWIN64
#include <unistd.h>
#endif
#define ABS(a) ((a<0)?-a:a)
#include "libtpt_hr.h"

#include "atpeven.h"
#include "hopatte.h"
#include "hopattr.h"
#include "hopdico.h"
#include "hopempl.h"
#include "hophabs.h"
#include "hophora.h"
#include "hophorc.h"
#include "hopmemo.h"
#include "hopmoti.h"
#include "hopplag.h"
#include "hoppoin.h"
#include "hopprof.h"
#include "hopproe.h"
#include "hopsech.h"
#include "hoptemp.h"
#include "atpcdcg.h"

struct meuh
{
int a;
} pouet;

#include "hrdjou1.qhe"
static struct S_POIN poin [MAX_POINTAGE];

 static struct {
	char wecr [sizeof(struct S_JOU1) + sizeof(poin)];
	char wTitreMemo [S_TEXT_TITRE_SIZE*COEF_UNICODE];
	char wTitreCons [S_TEXT_TITRE_SIZE*COEF_UNICODE];
	char wTitreSech [S_TEXT_TITRE_SIZE*COEF_UNICODE];
	char wTitreAnom [S_TEXT_TITRE_SIZE*COEF_UNICODE];
	char wTitreAbse [S_TEXT_TITRE_SIZE*COEF_UNICODE];
	char non_trouve [S_MESS_LIB_SIZE*COEF_UNICODE];
	char wInMod;
	char wInmiss;
	char wTpsNeg;
	char conf;
	int  nbp;
	int	 acces;
} Work;

void HRRJOU_LectureMemo(void);
void HRRJOU_LectureAnalytique(char *);
void HRRJOU_ActionZOOMB(void);
void HRRJOU_ActionMODAL(void);
void HRRJOU_LectureAbsencesConsignes(void);
int  HRRJOU_ActionBUTTON_CALCUL(void);
int  HRRJOU_ControleAnalytique(char *, char *, char *, short int, short int);
void HRRJOU_UpdateAnalytique(char *, hqt_handle, int);
int  HRRJOU_ControleHoraire(char);
int  HRRJOU_LectureGlobale(void);
int  HRRJOU_LecturePointages(char *);
void HRRJOU_InitElements(void);
int  HRRJOU_ActionSET(void);

/** \ingroup Module_HR_Fonctions
\brief <b>Fonction JOU du module HR</b>
*/
void HRRJOU(char *Mode, char *PtXML)
{
struct S_JOU1 wh1;
int code_action;

	DATESYS(GetGL()->DATJOU);

	XmlMapping((char *)&JOU1,
				JOU1.ZHJOU,
				sizeof(JOU1),
				(char *)&Work,
				sizeof(Work),
				NomChamp,
				LgChamp,
				DepChamp,
				NBCHAMP,
				PtXML,
				NomArray,
				SizeArray,
				NbColArray,
				NbLigArray,
				NBARRAY);

	HRRJOU_InitElements();

	init(wh1,' ');
	init(poin,' ');
	memcpy(&wh1, Work.wecr, sizeof(wh1));
	memcpy(&poin, Work.wecr + sizeof(wh1), sizeof(poin));

	switch (code_action = XML_CodeAction(Mode))
	{
		case ACTION_GET:
		case ACTION_NOCONF:
			Work.conf = ' ';
			HRRJOU_LectureGlobale();
			break;

		case ACTION_MODAL:
			HRRJOU_ActionMODAL();
			break;

		case ACTION_RETMODAL:
			HRRJOU_LectureGlobale();
			break;

		case ACTION_ZOOMB:
			HRRJOU_ActionZOOMB();
			break;

		case ACTION_ARRCHOICE:
			if (memcmp(getReq()->fctspe, "MMOT", 4) == 0
			||  memcmp(getReq()->fctspe, "MVAL", 4) == 0)
				HRSJOU_ActionArrchoice(1, "ABS", getReq()->fctspe, (char *)&JOU1.Z3, (char *)&wh1.Z3, Work.acces);
			else if (memcmp(getReq()->fctspe, "CMOT", 4) == 0
				||   memcmp(getReq()->fctspe, "CVAL", 4) == 0)
				HRSJOU_ActionArrchoice(2, "CNS", getReq()->fctspe, (char *)&JOU1.Z4, (char *)&wh1.Z4, Work.acces);
			break;

		case ACTION_BUTTON:
			if (memcmp(getReq()->fctspe, "CALCUL", 6) == 0
			||  memcmp(getReq()->fctspe, "BTN_ANA", 7) == 0)
			{
				if (HRRJOU_ActionBUTTON_CALCUL() == 0)
					HRRJOU_LectureGlobale();
			}

			if (memcmp(getReq()->fctspe, "VISA", 4) == 0)
			{
				if (HRSJOU_ActionVisa(JOU1.MATRI, JOU1.DATE) == 0)
					HRRJOU_LectureGlobale();
			}
			break;

		case ACTION_SET:
			if (HRRJOU_ActionSET() == 0)
				HRRJOU_LectureGlobale();
			break;

		default :
			return;
	}

	GenXmlData((char *)&JOU1,
				JOU1.ZHJOU,
				sizeof(JOU1),
				(char *)&Work,sizeof(Work),
				NomChamp,
				LgChamp,
				DepChamp,
				NBCHAMP,
				NomArray,
				SizeArray,
				NbColArray,
				NbLigArray,
				NBARRAY);
}

/*************************************************************************/
/*				DEFINITIONS DES	FONCTIONS INTERNES						 */
/*************************************************************************/

void HRRJOU_LectureMemo(void)
{
struct _TCB *T_MEMO;
struct S_MEMO MEMO;
char wTitreMemo [S_TEXT_TITRE_SIZE*COEF_UNICODE];
char wDate[8];

	/* Lecture du titre du mémo */
	if (comp(Work.wTitreMemo, " ") == 0)
		CMNTEXT_LireLabItem(getReq()->fct, "GRP_3/PAGE3", 11, Work.wTitreMemo, sizeof(Work.wTitreMemo));

	T_MEMO = DB_Open("hopmemo", sizeof(MEMO));

	/* Lecture du mémo */
	DATECTL(wDate, JOU1.DATE);
	DB_Setkey(T_MEMO, 1, JOU1.MATRI, sizeof(JOU1.MATRI), SQLTYPE_CHR);
	DB_Setkey(T_MEMO, 2, wDate, sizeof(wDate), SQLTYPE_DAT);
	DB_Setkey(T_MEMO, 3, "JOU", 3, SQLTYPE_CHR);
	init(MEMO, ' ');
	DB_Exec(T_MEMO, "select TEXTE,DATCRE,UTILCRE,DATMOD,UTILMOD",
					"from hopmemo",
					"where FONCTION=:3 and MATRI=:1 and DAT=$FmtD:2",
					NULL, (char *)&MEMO, NULL, NULL);

	init(wTitreMemo , ' ');
	move(wTitreMemo, Work.wTitreMemo);

	if (DB_Fetch(T_MEMO, Normal) == 0)
	{
		move(JOU1.MEMO, MEMO.TEXTE);
		DATEFOR(JOU1.DATCREMEMO, MEMO.DATCRE);
		move(JOU1.UTILCREMEMO, MEMO.UTILCRE);
		DATEFOR(JOU1.DATMODMEMO, MEMO.DATMOD);
		move(JOU1.UTILMODMEMO, MEMO.UTILMOD);

		memcpy(wTitreMemo + findb(wTitreMemo), " (*)", 4);
	}
	else
	{
		init(JOU1.MEMO, ' ');
		init(JOU1.DATCREMEMO, ' ');
		init(JOU1.UTILCREMEMO, ' ');
		init(JOU1.DATMODMEMO, ' ');
		init(JOU1.UTILMODMEMO, ' ');
	}

	GenXmlDyndesc("GRP_3/PAGE3", 11, wTitreMemo, (short int)findb(wTitreMemo), NULL, 0);

	DB_Close_Table(T_MEMO);
}

void HRRJOU_LectureAnalytique(char *date)
{
struct _TCB *T_EVEN = NULL;
struct S_ateven ateven;
struct _TCB *T_CDCG = NULL;
struct S_ATCDCG atcdcg;

char wDate[8];
int evenModified = 0;
short int iHreFin, iHreDeb;
int R_comp;
int i,j;

	if (isModulePresent(MODULE_ANA) == 0)
	{
		T_EVEN = DB_Open_Exist("atpeven",sizeof(ateven));

		if (T_EVEN != NULL)
		{
			T_CDCG = DB_Open_Exist("atpcdcg",sizeof(atcdcg));

			evenModified = 0;

			DATECTL(wDate, JOU1.DATE);
			move(ateven.EVENEMENT, "IMPUTA");
			DB_Setkey(T_EVEN, 1, JOU1.MATRI, sizeof(JOU1.MATRI),SQLTYPE_CHR);
			DB_Setkey(T_EVEN, 2, wDate, sizeof(wDate), SQLTYPE_DAT);
			DB_Setkey(T_EVEN, 3, ateven.EVENEMENT, sizeof(ateven.EVENEMENT), SQLTYPE_CHR);

			DB_Exec(T_EVEN, "select *",
							"from atpeven",
							"where MATRI=:1 and DATE_JOUR=$FmtD:2 and "
							"EVENEMENT=:3",
							"order by DATE_DEB, ORDRE",
							(char *)&ateven, NULL, NULL);

			i = 0;
			while (DB_Fetch(T_EVEN,Normal) == 0)
			{
				if (i >= MAX_ACTI)
					break;

				/* Evénement modifié : on bloque  */
				if (ateven.INDIC == '1'
				||  ateven.INDIC == '2')
					evenModified = 1;

				iHreDeb = ateven.HEURE_DEB;
				if ((R_comp = memcmp(ateven.DATE_DEB, date, 8)) != 0)
				{
					if (R_comp > 0)
						iHreDeb += 24*GetPH()->HINT;
					else
						if (R_comp < 0)
							iHreDeb -= 24*GetPH()->HINT;
				}
				hre1for(JOU1.Z5 [i].HREDEB, iHreDeb);

				iHreFin = ateven.HEURE_FIN;
				if ((R_comp = memcmp(ateven.DATE_FIN, date, 8)) != 0)
				{
					if (R_comp > 0)
						iHreFin += 24*GetPH()->HINT;
					else
						if (R_comp < 0)
							iHreFin -= 24*GetPH()->HINT;
				}
				hre1for(JOU1.Z5 [i].HREFIN, iHreFin);

				move(JOU1.Z5[i].CDC, ateven.VALEUR01);
				move(JOU1.Z5[i].COMMENT, ateven.COMMEN);

				if (ateven.DUREE_EVE != num1_LV)
				{
					heu2for(j, ateven.DUREE_EVE);
					num2for(JOU1.Z5[i].DUREE, 2, num2_LV, j);
				}

				if (comp(ateven.UTILMOD, " ") == 0)
					move(JOU1.Z5[i].UTILMOD, ateven.UTILCRE);
				else
					move(JOU1.Z5[i].UTILMOD, ateven.UTILMOD);

				move(JOU1.Z5 [i].ORDRE, ateven.ORDRE);
				if (ateven.INDIC == '5' || ateven.INDIC == '4')
					JOU1.Z5 [i].MOD = ateven.INDIC;
				else
					JOU1.Z5 [i].MOD = ' ';

				if (T_CDCG != NULL)
				{
					DB_Setkey(T_CDCG,1,ateven.VALEUR01,sizeof(JOU1.Z5[i].CDC), SQLTYPE_CHR);
					DB_Exec(T_CDCG, "select CDCG, LIBELLE",
									"from atpcdcg",
									"where CDCG=:1",
									NULL, (char *)&atcdcg, NULL, NULL);

					if (DB_Fetch(T_CDCG, Normal) == 0)
					{
						move(JOU1.Z5[i].LIBELLE,atcdcg.LIBELLE);
					}
					else
						move(JOU1.Z5[i].LIBELLE, Work.non_trouve);
				}
				i++;
			}

			if (T_CDCG != NULL)
				DB_Close_Table(T_CDCG);

			DB_Close_Table(T_EVEN);
		}

		if (Work.wInMod == '0' || Work.wInMod == ' ')
		{
			GenXmlAuthDesc("GRP_2/PAGE2", PROTECT_Y, VISIBLE_N);
			GenXmlAuthDesc("BTN_ANA", PROTECT_Y, VISIBLE_N);
		}
		else
		{
			if (evenModified == 1)
				GenXmlAuthDesc("BTN_ANA", PROTECT_N, VISIBLE_Y);
			else
				GenXmlAuthDesc("BTN_ANA", PROTECT_Y, VISIBLE_N);

			if (Rech_DroitFonction("JOU", "GRP_2/PAGE2", 11) >= NIV_LECT)
				GenXmlAuthDesc("GRP_2/PAGE2", UNCHANGED, VISIBLE_Y);

			if (Work.wInMod == '1') /* analytique badgée */
			{
				GenXmlAuthDesc("ANA/HREDEB", PROTECT_N, VISIBLE_Y);
				GenXmlAuthDesc("ANA/HREFIN", PROTECT_N, VISIBLE_Y);
				GenXmlAuthDesc("ANA/DUREE", PROTECT_Y, VISIBLE_Y);
			}
			else if (Work.wInMod == '2') /* analytique déclarée */
			{
				GenXmlAuthDesc("ANA/HREDEB", PROTECT_Y, VISIBLE_N);
				GenXmlAuthDesc("ANA/HREFIN", PROTECT_Y, VISIBLE_N);
				GenXmlAuthDesc("ANA/DUREE", PROTECT_N, VISIBLE_Y);
			}
		}
	}
	else
	{
		GenXmlAuthDesc("GRP_2/PAGE2", PROTECT_Y, VISIBLE_N);
		GenXmlAuthDesc("BTN_ANA", PROTECT_Y, VISIBLE_N);
	}
}

void HRRJOU_ActionZOOMB(void)
{
	if (memcmp(getReq()->fctspe, "HR/CTR", 6) == 0)
		GenXmlKey( "MATRI", JOU1.MATRI, findb(JOU1.MATRI));

	if ((memcmp(getReq()->fctspe, "AT/EVE", 6) == 0
	||   memcmp(getReq()->fctspe, "HR/HIN", 6) == 0)
	&&  (comp(JOU1.MATRI," ") != 0
	||   comp(JOU1.DATE," ") != 0))
	{
		GenXmlKey("MATRI",JOU1.MATRI, findb(JOU1.MATRI));
		if (memcmp(getReq()->fctspe, "AT/EVE", 6) == 0)
			GenXmlKey("DATE_JOUR",JOU1.DATE, findb(JOU1.DATE));
		else
			GenXmlKey("DATE", JOU1.DATE, findb(JOU1.DATE));
	}
	if (memcmp(getReq()->fctspe, "AT/LIS", 6) == 0)
		GenXmlKey("DONNEE0", "CDCG", 4);
}

void HRRJOU_ActionMODAL(void)
{
S_ETAT	E_data;
_RFILE	*F_data;
struct S_HPLN data ;
struct S_COLLECTIF wCOLLECT;

char N_data [FILE_NAME_SIZE] = "HORDAT/HRPLN";
char datmod [8];
char wMatri [10];

	init(datmod, ' ');

	/* Absences collectives */
	if (comp(getReq()->fctspe, "HR/CAB") == 0
	||  comp(getReq()->fctspe, "HR/CMV") == 0
	||  comp(getReq()->fctspe, "HR/CPL") == 0
	||  comp(getReq()->fctspe, "HR/CAF") == 0
	||  comp(getReq()->fctspe, "HR/CSH") == 0
	||  comp(getReq()->fctspe, "HR/CHO") == 0
	||  comp(getReq()->fctspe, "HR/CPR") == 0)
	{
		init(wCOLLECT, ' ');
		memcpy(wCOLLECT.FCT, GetMN()->FCTCUR [GetMN()->INDZOOM] +3, 3);
		wCOLLECT.PERIODE = 'P';
		wCOLLECT.CHXRESTRI = '5';
		memcpy(wCOLLECT.VALRESTRI,JOU1.MATRI,sizeof(JOU1.MATRI));
		DATECTL(datmod,JOU1.DATE);
		memcpy(wCOLLECT.DATDEB,datmod,(size_t)8);
		memcpy(wCOLLECT.DATFIN,datmod,(size_t)8);

        if (comp(getReq()->fctspe, "HR/CAF") == 0)
        {
            init(wCOLLECT.DATFIN, ' ');
        }

		init(GetCL()->BUFFER,' ');
		memcpy(GetCL()->BUFFER, &wCOLLECT, sizeof(wCOLLECT));

		E_data.MODE = Mode_cre;
		E_data.ACCES= Rrn_EQ;
		SouvUser(F_data,N_data,sizeof(data),E_data);
		init(data, ' ');
		move(data.MATRI, JOU1.MATRI);
		Secr(F_data,data,E_data);
		Sfer(F_data,E_data);
	}
	/* Modale des auteurs */
	if (comp(getReq()->fctspe, "CM/AUT") == 0)
	{
		init(GetCL()->BUFFER, ' ');
		init(wMatri, ' ');

		move(wMatri, getSelect()->value);

		memcpy(GetCL()->BUFFER,wMatri,sizeof(wMatri));
	}

	/* Modale de géolocalisation */
	if (comp(getReq()->fctspe, "HR/MAP") == 0)
	{
		init(GetCL()->BUFFER, ' ');
		memcpy(GetCL()->BUFFER,JOU1.MATRI,LG_MATRI);
		DATECTL(datmod,JOU1.DATE);
		memcpy(GetCL()->BUFFER+LG_MATRI,datmod,LG_DATE_INT);
	}
}

void HRRJOU_LectureAbsencesConsignes(void)
{
struct _TCB *T_HABS;
struct S_HABS HABS;

char wTitreCons [S_TEXT_TITRE_SIZE*COEF_UNICODE];
char wTitreAbse [S_TEXT_TITRE_SIZE*COEF_UNICODE];
char wDate[8];
int nba;
int k, l;
char wNbAbs[2];

	nba = 0;
	init(JOU1.Z3,' ');
	init(JOU1.Z4,' ');

	init(HABS, ' ');

	/* Lecture du titre de la consigne */
	if (comp(Work.wTitreCons, " ") == 0)
		CMNTEXT_LireLabItem(getReq()->fct, "GRP_5/PAGE2", 11, Work.wTitreCons, sizeof(Work.wTitreCons));

	/* Lecture du titre de l'absence */
	if (comp(Work.wTitreAbse, " ") == 0)
		CMNTEXT_LireLabItem(getReq()->fct, "GRP_5/PAGE1", 11, Work.wTitreAbse, sizeof(Work.wTitreAbse));

	T_HABS = DB_Open("hophabs", sizeof(HABS));

	DATECTL(wDate, JOU1.DATE);
	DB_Setkey(T_HABS, 1, JOU1.MATRI, sizeof(JOU1.MATRI),SQLTYPE_CHR);
	DB_Setkey(T_HABS, 2, wDate, sizeof(wDate),SQLTYPE_DAT);
	DB_Exec(T_HABS, "select *",
					"from hophabs",
					"where MATRI=:1 and DAT=$FmtD:2 ",
					"order by HRADEB, VALORIS",
					(char *)&HABS,NULL,NULL);

	k = 0;
	l = 0;

	while (DB_Fetch(T_HABS,Normal) == 0)
	{
		if (nba >= MAX_ABSENCE)
			break;

		/* Consignes */
		if (HABS.MOTYPE == GetPG()->MOTYPE [4])
			k = HRSJOU_LectureAbsence("CNS", (char *)&JOU1.Z4, (char *)&HABS, k, Work.acces);
		else
			l = HRSJOU_LectureAbsence("ABS", (char *)&JOU1.Z3, (char *)&HABS, l, Work.acces);

		++nba;
	}

	init(wTitreCons ,' ');
	move(wTitreCons,Work.wTitreCons);
	init(wTitreAbse ,' ');
	move(wTitreAbse,Work.wTitreAbse);

	if (k > 0)
	{
		INTCHAR(wNbAbs, k, 2);
		memcpy(wTitreCons + findb(wTitreCons), " (", 2);
		memcpy(wTitreCons + findb(wTitreCons), wNbAbs, 2);
		memcpy(wTitreCons + findb(wTitreCons), ")", 1);
	}
	if (l > 0)
	{
		INTCHAR(wNbAbs, l, 2);
		memcpy(wTitreAbse + findb(wTitreAbse), " (", 2);
		memcpy(wTitreAbse + findb(wTitreAbse), wNbAbs, 2);
		memcpy(wTitreAbse + findb(wTitreAbse), ")", 1);
	}

	GenXmlDyndesc("GRP_5/PAGE1",11, wTitreAbse,(short int)findb(wTitreAbse),NULL, 0);
	GenXmlDyndesc("GRP_5/PAGE2",11, wTitreCons,(short int)findb(wTitreCons),NULL, 0);

	DB_Close_Table(T_HABS);
}

int HRRJOU_ActionBUTTON_CALCUL(void)
{
struct _TCB *T_EVEN = NULL;
struct S_ateven ateven;
char wDate[8];

	if (GetMN()->FONCNIV [GetMN()->INDZOOM] == '1')
	{
		GenXmlMsg(95, "SRV", NULL, " ", MsgErr);
		return -1;
	}
	if (comp(JOU1.MATRI," ") == 0)
	{
		GenXmlMsg(11, "EMP", "MATRI", " ", MsgErr);
		return -1;
	}

	if (comp(JOU1.DATE," ") == 0)
	{
		GenXmlMsg(20, "SRV", "DATE", " ", MsgErr);
		return -1;
	}

	if (memcmp(getReq()->fctspe,"BTN_ANA",7) == 0
	&&  isModulePresent(MODULE_ANA) == 0)
	{
		/* confirmation */
		if (Work.conf == ' ')
		{
			Work.conf = '1';
			GenXmlMsg(62, "JOU", NULL, " ", MsgConf);
			return 1;
		}

		T_EVEN = DB_Open_Exist("atpeven",sizeof(ateven));

		if (T_EVEN != NULL)
		{
			DATECTL(wDate, JOU1.DATE);
			DB_Setkey(T_EVEN, 1, JOU1.MATRI, sizeof(JOU1.MATRI),SQLTYPE_CHR);
			DB_Setkey(T_EVEN, 2, wDate, sizeof(wDate), SQLTYPE_DAT);
			DB_Setkey(T_EVEN, 3, "IMPUTA", 6, SQLTYPE_CHR);
			DB_Exec(T_EVEN, "update",
							"atpeven",
							"set INDIC='2' where MATRI=:1 and DATE_JOUR=$FmtD:2 "
							"and EVENEMENT=:3 ",
							NULL, (char *)&ateven, NULL, NULL);
			DB_Close_Table(T_EVEN);
		}
	}

	DATECTL(wDate, JOU1.DATE);
	if (CMN_AppelCalcul(JOU1.MATRI, wDate, NULL, '1', NULL, '2', GetGL()->DATJOU) < 0)
	   return -1;
	else
	   return 0;
}

int HRRJOU_ControleAnalytique(char *w_ana, char *matri, char *date, short int prodeb, short int profin)
{
struct _TCB *T_CDCG = NULL;
struct S_ATCDCG atcdcg;

struct S_JOU1 wh1;
struct Analyt{
	char HREDEB	 [6];
	char HREFIN  [6];
	char DUREE   [6];
	char CDC	 [10];
	char LIBELLE [40*COEF_UNICODE];
	char COMMENT [100*COEF_UNICODE];
	char UTILMOD [LG_UTIL];
	char ORDRE	 [4];
	char MOD;
	} sANA;

struct {
	short int HREDEB;
	short int HREFIN;
	short int DUREE;
	short int IND;
} W_ANA [MAX_ACTI];

char *StrWhere = NULL;
int LgStrWhere;
int retVal = 0;
int i, j, m;

	if (isModulePresent(MODULE_ANA) != 0)
		return 0;

	memcpy(&W_ANA, w_ana, sizeof(W_ANA));

	init(wh1,' ');
	memcpy(&wh1, Work.wecr, sizeof(wh1));

	T_CDCG = DB_Open_Exist("atpcdcg",sizeof(atcdcg));

	i = 0;
	j = 0;

	/* Contrôle des changements des activités */
	while (i < MAXLIG_ANA)
	{
		if (comp(JOU1.Z5 [j].ORDRE, " ") == 0
		&&  comp(JOU1.Z5 [j].CDC, " ") != 0)
		{
			JOU1.Z5 [j].MOD = '1';
			retVal = 1;
			j++;
			continue;
		}

		if (j == MAXLIG_ANA)
			break;

		init(sANA, ' ');
		memcpy(&sANA, &wh1.Z5 [i], sizeof(sANA));

		if (comp(JOU1.Z5 [j].ORDRE, sANA.ORDRE) != 0)
		{
			j++;
			continue;
		}
		else
		{
			if (comp(JOU1.Z5 [j].CDC, sANA.CDC) == 0
			&&  comp(JOU1.Z5 [j].HREDEB, sANA.HREDEB) == 0
			&&  comp(JOU1.Z5 [j].HREFIN, sANA.HREFIN) == 0
			&&  comp(JOU1.Z5 [j].COMMENT, sANA.COMMENT) == 0
			&&  comp(JOU1.Z5 [j].DUREE, sANA.DUREE) == 0)
			{
				;
			}
			else
			{
				JOU1.Z5 [j].MOD = '1';
				retVal = 1;
			}
		}

		j++;
		i++;
	}

	m = 0;
	for (i=0;i<MAXLIG_ANA;i++)
	{
		if (retVal != 1)
			break;

		if (Work.wInMod == '1'
		&&  comp(JOU1.Z5 [i].HREDEB," ") == 0
		&&  comp(JOU1.Z5 [i].HREFIN," ") == 0
		&&  comp(JOU1.Z5 [i].CDC, " ") != 0)
		{
			GenXmlArError(29, "SRV", "ANA/HREDEB", i, " ");
			GenXmlArError(30, "SRV", "ANA/HREFIN", i, " ");
		}

		if (Work.wInMod == '1'
		&& (comp(JOU1.Z5 [i].HREDEB," ") != 0
		||  comp(JOU1.Z5 [i].HREFIN," ") != 0))
		{
			if (hre1ctl(JOU1.Z5 [i].HREDEB,W_ANA [m].HREDEB) < 0)
			{
				GenXmlArError(24,"SRV","ANA/HREDEB",i," ");
				m++;
				continue;
			}

			if (hre1ctl(JOU1.Z5 [i].HREFIN,W_ANA [m].HREFIN) < 0)
			{
				GenXmlArError(24,"SRV","ANA/HREFIN",i," ");
				m++;
				continue;
			}

			if (W_ANA [m].HREDEB == num1_HV)
				GenXmlArError(29, "SRV", "ANA/HREDEB", i, " ");

			if (W_ANA [m].HREFIN == num1_HV)
				GenXmlArError(30, "SRV", "ANA/HREFIN", i, " ");

			/* Contrôle Heure de fin sup ou égale à Heure de début */
			if (W_ANA [m].HREDEB > W_ANA [m].HREFIN
			&&  Work.wTpsNeg != '1')
			{
				GenXmlArError(28, "SRV", "ANA/HREDEB", i, " ");
				GenXmlArError(28, "SRV", "ANA/HREFIN", i, " ");
			}

			W_ANA [m].IND = i;

			/* Contrôle si HRETRAI est dans les limites du profil */
			if (W_ANA [m].HREDEB != num1_HV
			&& (W_ANA [m].HREDEB < prodeb
			||  W_ANA [m].HREDEB >= profin))
				GenXmlArError(30,"JOU","ANA/HREDEB",i," ");

			if (W_ANA [m].HREFIN != num1_HV
			&& (W_ANA [m].HREFIN < prodeb
			||  W_ANA [m].HREFIN >= profin))
				GenXmlArError(30,"JOU","ANA/HREFIN",i," ");

			m++;
			continue;
		}

		if (Work.wInMod == '2'
		&&  comp(JOU1.Z5 [i].DUREE," ") != 0)
		{
			W_ANA [m].HREDEB = num1_LV;
			W_ANA [m].HREFIN = num1_LV;
			if (hre1ctl(JOU1.Z5 [i].DUREE,W_ANA [m].DUREE) < 0)
				GenXmlArError(24,"SRV","ANA/DUREE",i," ");

			W_ANA [m].IND = i;

			m++;
		}
	}

	/* Contrôle ordre chronologique des activités */
	if (retVal == 1
	&&  Work.wInMod == '1')
	{
		for (i=1;i<m;i++)
		{
			if (W_ANA [i].HREDEB != num1_HV
			&&  W_ANA [i-1].HREFIN != num1_HV
			&&  W_ANA [i].HREDEB < W_ANA [i-1].HREFIN)
			{
				GenXmlArError(33,"JOU","ANA/HREFIN",W_ANA [i-1].IND," ");
				GenXmlArError(33,"JOU","ANA/HREDEB",W_ANA [i].IND," ");
			}
		}
	}

	if (T_CDCG != NULL && Work.wInMod > '0')
	{
		LgStrWhere = 50;
		StrWhere = HQmalloc(LgStrWhere);
		strcpy(StrWhere, "where CDCG=:1");
		HRSSLU_TestSite(&StrWhere, &LgStrWhere, "CDCG", "ATPCDCG");

		for (i=0;i<MAXLIG_ANA;i++)
		{
			if (comp(JOU1.Z5[i].CDC, " ") == 0
			||  *JOU1.Z5[i].CDC == '*')
				continue;

			DB_Setkey(T_CDCG, 1, JOU1.Z5[i].CDC, sizeof(JOU1.Z5[i].CDC), SQLTYPE_CHR);
			DB_Exec(T_CDCG, "select CDCG",
							"from atpcdcg",
							StrWhere,
							NULL, (char *)&atcdcg, NULL, NULL);

			if (DB_Fetch(T_CDCG, Normal) != 0)
				GenXmlArError(63, "JOU", "ANA/CDC", i, JOU1.Z5[i].CDC);
		}

		HQfree(StrWhere);
	}

	if (getNbError() != 0)
		retVal = -1;

	DB_Close_Table(T_CDCG);

	memcpy(w_ana, &W_ANA, sizeof(W_ANA));

	return retVal;
}

void HRRJOU_UpdateAnalytique(char *w_ana, hqt_handle pTable_hjoun, int CdcChanged)
{
struct _TCB *T_EVEN = NULL;
struct S_ateven ateven;
struct {
	short int HREDEB;
	short int HREFIN;
	short int DUREE;
	short int IND;
} W_ANA [MAX_ACTI];
int i, n, nOrdre;
char wHjou_matri[LG_MATRI];
char wHjou_date[LG_DATE_INT];
char wHjou_profil[LG_PROFIL];
char wHjou_recalc=' ';

	if (isModulePresent(MODULE_ANA) != 0)
		return;

	DAO_getAttribute(pTable_hjoun, "MATRI", wHjou_matri);
	DAO_getAttribute(pTable_hjoun, "DAT", wHjou_date);
	DAO_getAttribute(pTable_hjoun, "PROFIL", wHjou_profil);

	memcpy(&W_ANA, w_ana, sizeof(W_ANA));

	T_EVEN = DB_Open_Exist("atpeven",sizeof(ateven));

	if (CdcChanged == 1
	&&  T_EVEN != NULL)
	{
		DB_Setkey(T_EVEN, 1, wHjou_matri, LG_MATRI,SQLTYPE_CHR);
		DB_Setkey(T_EVEN, 2, wHjou_date, LG_DATE_INT, SQLTYPE_DAT);
		DB_Setkey(T_EVEN, 3, "IMPUTA", 6, SQLTYPE_CHR);
		DB_Exec(T_EVEN, "delete",
						"from atpeven",
						"where MATRI=:1 and DATE_JOUR=$FmtD:2 and "
						"EVENEMENT=:3",
						NULL, (char *)&ateven, NULL, NULL);
	}

	n = 0;
	nOrdre = 0;
	for (i=0;i<MAXLIG_ANA;i++)
	{
		if (n > MAX_ACTI)
			continue;

		if (comp(JOU1.Z5 [i].CDC," ") != 0
		&&  CdcChanged == 1)
		{
			nOrdre++;
			init(ateven,' ');
			move(ateven.EVENEMENT, "IMPUTA");
			move(ateven.CLE_POP, GetPS()->HORSECT);
			move(ateven.MATRI,wHjou_matri);

			INTCHAR(ateven.ORDRE,nOrdre,4);

			move(ateven.DATE_JOUR, wHjou_date);
			move(ateven.DATE_DEB, wHjou_date);
			move(ateven.DATE_FIN, wHjou_date);

			ateven.HEURE_DEB = W_ANA[n].HREDEB;
			ateven.HEURE_FIN = W_ANA[n].HREFIN;
			if (Work.wInMod == '2')
				ateven.DUREE_EVE = W_ANA[n].DUREE;
			else
				ateven.DUREE_EVE = W_ANA[n].HREFIN - W_ANA[n].HREDEB;

			move(ateven.VALEUR01, JOU1.Z5 [i].CDC);
			move(ateven.COMMEN, JOU1.Z5 [i].COMMENT);
			move(ateven.PROFIL, wHjou_profil);

			if (comp(JOU1.Z5 [i].UTILMOD, " ") == 0)
				move(ateven.UTILCRE, GetPS()->MATRI_AUTH);
			else
				move(ateven.UTILCRE, JOU1.Z5 [i].UTILMOD);

			move(ateven.DATCRE, GetGL()->DATJOU);

			if (JOU1.Z5 [i].MOD == '1')
			{
				move(ateven.UTILMOD, GetPS()->MATRI_AUTH);
				move(ateven.DATMOD, GetGL()->DATJOU);
			}
			else
				move(ateven.UTILMOD, JOU1.Z5 [i].UTILMOD);

			ateven.POURCENT = 0;
			ateven.DUREE_INFO = 0;

			if (JOU1.Z5 [i].MOD == '5' || JOU1.Z5 [i].MOD == '4')
				ateven.INDIC = JOU1.Z5 [i].MOD;
			else
				ateven.INDIC = '1';
			wHjou_recalc = '1';
			DAO_setAttribute(pTable_hjoun, "RECALC", &wHjou_recalc);

			DB_Insert(T_EVEN,(char *)&ateven);
		}
		if ((Work.wInMod == '1'
		&&  (comp(JOU1.Z5 [i].HREDEB," ") != 0
		||   comp(JOU1.Z5 [i].HREFIN," ") != 0))
		||  (Work.wInMod == '2'
		&&   comp(JOU1.Z5 [i].DUREE," ") != 0))
			n++;
	}

	DB_Close_Table(T_EVEN);
}

void HRSCRJOU(char *BufXML, char *NotUsed)
{
char wDate [10];

	if (isModulePresent(MODULE_ANA) != 0)
		GenXmlAuthDesc("GRP_2/PAGE2", PROTECT_N, VISIBLE_N);

	if ( Rech_DroitFonction("JOU", NULL, 0) == NIV_LECT
	|| Rech_DroitFonction("JOU", "BTN_CALCUL", 10) <= NIV_LECT )
		GenXmlAuthDesc("BTN_CALCUL", PROTECT_Y, VISIBLE_N);

	GenXmlAuthDesc("GRP_4", PROTECT_Y, VISIBLE_Y);
	if (GetPS()->DRVISA != 1)
		GenXmlAuthDesc("GRP_32", PROTECT_Y, VISIBLE_Y);

	if (GetPS()->TYPE_UTIL == 'D')
	{
		WriteStrData((struct _BUFOUT*)BufXML, "MATRI", GetPS()->MATRI, sizeof(GetPS()->MATRI));
		DATEFOR(wDate,GetGL()->DATJOU);
		WriteStrData((struct _BUFOUT*)BufXML, "DATE", wDate, sizeof(wDate));
		GenXmlAuthDesc("MATRI", PROTECT_Y, VISIBLE_Y);
	}
	else if	(GetMN()->FLGZOOM == 0)
	{
		WriteStrData((struct _BUFOUT*)BufXML, "MATRI", GetCL()->MATRI, sizeof(GetCL()->MATRI));
		DATEFOR(wDate,GetCL()->DATE);
		WriteStrData((struct _BUFOUT*)BufXML, "DATE", wDate, sizeof(wDate));
		GenXmlAuthDesc("MATRI", PROTECT_N, VISIBLE_Y);
	}
	else
		GenXmlAuthDesc("MATRI", PROTECT_N, VISIBLE_Y);
}

int HRRJOU_ControleHoraire(char jour)
{
struct _TCB *T_HORC;
struct _TCB *T_PROE;
struct _TCB *T_ATTE;
struct S_PROF PROF;
struct S_HORA HORA;
struct S_ATTR ATTR;
struct S_JOU1 wh1;
char z_repet [3];
short int w_rang;
int i;
int retVal = 0;

	init(wh1,' ');
	init(PROF, ' ');
	memcpy(&wh1, Work.wecr, sizeof(wh1));

	/* Changement du rang */
	if (comp(JOU1.RANG,wh1.RANG) != 0
	||  comp(JOU1.HORCODE,wh1.HORCODE) != 0)
	{
		T_HORC = DB_Open("hophorc", sizeof(struct S_HORC));

		move(wh1.HORCODE,JOU1.HORCODE);

		if (T_Tmem(T_HORC, &HORA, sizeof(HORA), "HORCODE", JOU1.HORCODE ,sizeof(JOU1.HORCODE)) < 0)
			GenXmlMsg(1, "HOR", "HORCODE", JOU1.HORCODE, MsgErr);
		else
		{
			if (HORA.INACTIF == '1')
				GenXmlMsg(31, "HOR", "HORCODE", JOU1.HORCODE, MsgErr);

			if (num1ctl(JOU1.RANG,0,num1_HV,w_rang) < 0)
				GenXmlMsg(26, "HOR", "RANG", " ", MsgErr);
			else
			{
				if (HORA.HORTYPE == GetPG()->PERIODE [2])
					HORA.REPET = HORA.REPET / 7;
				if (w_rang < 1  || w_rang > HORA.REPET)
				{
					INTCHAR(z_repet,HORA.REPET,3);
					GenXmlMsg(25, "HOR", "RANG", z_repet, MsgErr);
					DB_Close_Table(T_HORC);
					return -1;
				}
			}

			if (HORA.HORTYPE == GetPG()->PERIODE [2])
				i = (w_rang -1)* 7 + jour - '1';
			else
				i = w_rang - 1;
			move(JOU1.PROFIL,HORA.PROFIL [i]);
			move(JOU1.ATTRIB,HORA.ATTRIB [i]);
		}
		DB_Close_Table(T_HORC);
	}

    /* Changement de profil */
	if (comp(JOU1.PROFIL,wh1.PROFIL) != 0)
	{
		T_PROE = DB_Open("hopproe", sizeof(struct S_PROE));

		if (T_Tmem(T_PROE, &PROF, sizeof(PROF), "PROFIL", JOU1.PROFIL, sizeof(JOU1.PROFIL)) < 0)
			GenXmlMsg(1, "PRO", "PROFIL", JOU1.PROFIL, MsgErr);
		else
		{
			hre1for(JOU1.PROFIN,PROF.PROFIN);
			move(wh1.PROFIL,JOU1.PROFIL);
		}

		if (PROF.INACTIF == '1')
			GenXmlMsg(31, "PRO", "PROFIL", JOU1.PROFIL, MsgErr);

		DB_Close_Table(T_PROE);
	}

    /* Changement de l'attribution automatique */
	if (comp(JOU1.ATTRIB,wh1.ATTRIB) != 0)
	{
		if (comp(JOU1.ATTRIB, " ") == 0)
			init(wh1.ATTRIB, ' ');
		else
		{
			T_ATTE = DB_Open("hopatte", sizeof(struct S_ATTE));

			if (T_Tmem(T_ATTE, &ATTR, sizeof(ATTR), "ATTRIB", JOU1.ATTRIB, sizeof(JOU1.ATTRIB)) < 0)
				GenXmlMsg(1, "ATT", "ATTRIB", JOU1.ATTRIB, MsgErr);
			else
				move(wh1.ATTRIB,JOU1.ATTRIB);

			DB_Close_Table(T_ATTE);
		}
	}

	if (getNbError() != 0)
		retVal = -1;

	return retVal;
}

int HRRJOU_LectureGlobale(void)
{
struct _TCB *T_EMPL;
struct S_EMPL EMPL;
struct _TCB *T_SECH;
struct S_SECH SECH;
struct _TCB *T_PROE;
struct S_PROE PROE;
struct S_PROF PROF;
struct _TCB *T_ATTE;
struct _TCB *T_HORC;
struct S_HORA HORA;
struct S_ATTR ATTR;
struct _TCB *T_DICO;
struct S_DICO DICO;

char wTitreSech [S_TEXT_TITRE_SIZE*COEF_UNICODE];
char StrPret [S_MESS_LIB_SIZE*COEF_UNICODE];
char wDate [10];
char wHreFin [6];

short int iHreFin;
short int w_rang;
short int w_pretheo;
int nbp;
int iModProf = 0;
int acces;
int i,j,k;
int retVal = 0;

hqt_handle pTable_hjou = NULL;
short int wHjou_prodeb=0, wHjou_profin=0;
char wHjou_date[LG_DATE_INT];
char wHjou_sectaff [LG_HORSECT];
short int depla = 0;

	T_EMPL = DB_Open("hopempl", sizeof(EMPL));
	T_SECH = DB_Open("hopsech", sizeof(SECH));
	T_PROE = DB_Open("hopproe", sizeof(PROE));
	T_ATTE = DB_Open("hopatte", sizeof(struct S_ATTE));
	T_HORC = DB_Open("hophorc", sizeof(struct S_HORC));
	T_DICO = DB_Open("hopdico", sizeof(DICO));

	Work.conf = ' ';

	init(JOU1.Z5,' ');

	if (GetPS()->TYPE_UTIL == 'D')
		move(JOU1.MATRI, GetPS()->MATRI);

	if (GetPH()->MATRI == '0'
	&&  GetPH()->MINIMAT == GetPH()->MAXIMAT)
		CadrageMatri(JOU1.MATRI,GetPH()->MAXIMAT);

	init(poin,' ');

    /* BT11096 : sur la fonction JOU, on affiche les données perso de EMPL
    on effectue une lecture systématique de hopempl */
	DB_Setkey(T_EMPL, 1, JOU1.MATRI, sizeof(JOU1.MATRI),SQLTYPE_CHR);
	DB_Exec(T_EMPL, "select *",
					"from hopempl",
					"where MATRI=:1 ",NULL,
					(char *)&EMPL,NULL,NULL);

	if (DB_Fetch(T_EMPL,Normal) != 0)
	{
		GenXmlMsg(1, "EMP", "MATRI", JOU1.MATRI, MsgErr);
		goto Fin;
	}

	/* Contrôle de la date */
	if (comp(JOU1.DATE," ") == 0)
	{
		if (comp(EMPL.DATCALC,GetGL()->DATJOU) < 0)
			DATEFOR(JOU1.DATE,EMPL.DATCALC);
		if (comp(JOU1.DATE," ") == 0)
		{
			DATEFOR(JOU1.DATE,EMPL.DATTRAI);
			if (comp(EMPL.DATTRAI,GetGL()->DATJOU) > 0)
				DATEFOR(JOU1.DATE,GetGL()->DATJOU);
		}
		if (comp(JOU1.DATE, " ") == 0)
		{
			GenXmlMsg(15, "JOU", "DATE", JOU1.DATE, MsgErr);
			goto Fin;
		}
	}

	if (DATECTL(wHjou_date,JOU1.DATE) < 0)
	{
		GenXmlMsg(22, "SRV", "DATE", " ", MsgErr);
		goto Fin;
	}

	DATEFOR(JOU1.DATE,wHjou_date);

	if (CTRL_AccesHisto(wHjou_date, "DATE", 1) < 0)
		goto Fin;

	if (comp(wHjou_date,GetPS()->HISPLAN) > 0)
	{
		DATEFOR(wDate, GetPS()->HISPLAN);
		GenXmlMsg(14, "JOU", "DATE", wDate, MsgErr);
		goto Fin;
	}

	/* Test accès matri sur date */
	WriteKepContext(wHjou_date, " ", NULL);
	acces = HRSSLU_TestAccesMatri(JOU1.MATRI);

	Work.acces = acces;

	if (acces == AC_NOACCES)
	{
		GenXmlMsg(1, "EMP", "MATRI", JOU1.MATRI, MsgErr);
		goto Fin;
	}
	if (acces == AC_FICHE)
	{
		MessErrAccesMatri(JOU1.MATRI, wHjou_date, NULL, "MATRI", acces);
		goto Fin;
	}

	init(StrPret, ' ');
	if (acces != AC_PRET_PERIOD_TOT
	&&  acces != AC_EMPR_PERIOD_TOT)
	{
		GenXmlAuthDesc("LABPE", UNCHANGED, VISIBLE_N);
	}
	else
	{
		GenXmlAuthDesc("LABPE", UNCHANGED, VISIBLE_Y);
		FillLabelPret(StrPret, findb(StrPret), acces);
		move(JOU1.LABPE, StrPret);
	}

	if (acces == AC_NORMAL
	||  acces == AC_EMPR_PERIOD_TOT )
	{
		if (comp(wHjou_date,GetPS()->GTLIMPA) < 0
		||  comp(wHjou_date,GetPS()->GTLIMFU) > 0)
		{
			GenXmlAuthDesc(NULL, PROTECT_LECTURE, UNCHANGED);
			GenXmlAuthDesc("BTN_CALCUL", PROTECT_Y, UNCHANGED);

			Work.acces = AC_PRET_PERIOD_TOT;
		}
		else
		{
			GenXmlAuthDesc(NULL, PROTECT_NIVFCT, UNCHANGED);
			GenXmlAuthDesc("BTN_CALCUL", PROTECT_N, UNCHANGED);
		}
	}
	else if (acces == AC_PRET_PERIOD_TOT
		 ||  acces == AC_READONLY)
	{
		GenXmlAuthDesc(NULL, PROTECT_LECTURE, UNCHANGED);
		GenXmlAuthDesc("BTN_CALCUL", PROTECT_Y, UNCHANGED);
	}

	/* Existence de la journée d'historique */
    pTable_hjou = CMPHJOU_init();

	DAO_Setkey(1, JOU1.MATRI, LG_MATRI, SQLTYPE_CHR);
	DAO_Setkey(2, wHjou_date, LG_DATE_INT,SQLTYPE_DAT);
    CMPHJOU_execute(pTable_hjou, "h.MATRI=:1 and h.DAT=$FmtD:2", NULL);
    if (CMPHJOU_fetch(pTable_hjou, Normal) != 0)
	{
		GenXmlMsg(15, "JOU", "DATE", JOU1.DATE, MsgErr);
		goto Fin;
	}
    move(JOU1.NOMPRE, EMPL.NOMPRE);
    CMPHJOU_getAttribute(pTable_hjou, "MATRI", JOU1.MATRI);
    CMPHJOU_getAttribute(pTable_hjou, "PROFIL", JOU1.PROFIL);
    CMPHJOU_getAttribute(pTable_hjou, "HORCODE", JOU1.HORCODE);
    CMPHJOU_getAttribute(pTable_hjou, "RANG", &w_rang);
	INTCHAR(JOU1.RANG, w_rang, 3);
    CMPHJOU_getAttribute(pTable_hjou, "ATTRIB", JOU1.ATTRIB);
    CMPHJOU_getAttributeFmt(pTable_hjou, "PROFIN", JOU1.PROFIN, sizeof(JOU1.PROFIN));
	CMPHJOU_getAttribute(pTable_hjou, "UTILMOD", JOU1.UTILVISA);
    CMPHJOU_getAttribute(pTable_hjou, "SECTORI", JOU1.SECTORI);
    CMPHJOU_getAttribute(pTable_hjou, "SECTAFF", wHjou_sectaff);

	if (T_TmemIgnoreSite(T_SECH, &SECH, sizeof(SECH), "HORSECT", JOU1.SECTORI, sizeof(JOU1.SECTORI)) >= 0)
		move(JOU1.LIBSECTORI, SECH.LIBCOURT);
	else
		move(JOU1.LIBSECTORI, Work.non_trouve);

	init(wTitreSech ,' ');
	move(wTitreSech,Work.wTitreSech);

	/* Info si matricule prêté ou non */
	if ( comp( wHjou_sectaff, " ") != 0 )
	{
		CMNMESS_LireMessage(12, "HR/DIJ", JOU1.STATUS,sizeof(JOU1.STATUS));
		move(JOU1.SECTAFF, wHjou_sectaff);

		if (T_TmemIgnoreSite(T_SECH, &SECH, sizeof(SECH), "HORSECT", JOU1.SECTAFF, sizeof(JOU1.SECTAFF)) >= 0)
			move(JOU1.LIBSECTAFF, SECH.LIBCOURT);
		else
			move(JOU1.LIBSECTAFF, Work.non_trouve);

		GenXmlAuthDesc("STATUS",  PROTECT_Y, VISIBLE_Y);
		GenXmlAuthDesc("SECTAFF",  PROTECT_Y, VISIBLE_Y);
		GenXmlAuthDesc("LIBSECTAFF",  PROTECT_Y, VISIBLE_Y);
		memcpy(wTitreSech + findb(wTitreSech), " (*)", 4);
	}
	else
	{
		GenXmlAuthDesc("STATUS",  PROTECT_Y, VISIBLE_N);
		GenXmlAuthDesc("SECTAFF",  PROTECT_Y, VISIBLE_N);
		GenXmlAuthDesc("LIBSECTAFF",  PROTECT_Y, VISIBLE_N);
	}

	GenXmlDyndesc("GRP_15",6, wTitreSech,(short int)findb(wTitreSech),NULL, 0);

	/* Alimentation du graphe des profils */
	init(JOU1.BARGRAPH, ' ');
	k = 0;

	/* Unité des heures */
	if (GetPH()->HINT == 60)
	{
		memcpy(JOU1.BARGRAPH, "[60]", 4);
		k+=4;
	}
	else
	{
		memcpy(JOU1.BARGRAPH, "[100]", 5);
		k+=5;
	}

    iModProf = HRSJOU_plage((char*)&PROF, JOU1.MATRI, JOU1.DATE, JOU1.PROFIL, &wHjou_profin);

	CMNLANG_Traduire_Libelle(JOU1.PROFIL, LG_PROFIL, PROF.LIBELLE, sizeof(PROF.LIBELLE), "hopproe", "LIBELLE", -1);
	move(JOU1.LIBPROFIL, PROF.LIBELLE);

	/* hdébut du graphe                                           */
	/*   si profil : calcul hdébut à partir de hfin(1ère plage) : */
	/*     si hfin(1ère plage) < 01.00 : hdébut = 12.00<          */
	/*     si hfin(1ère plage) < 13.00 : hdébut = 00.00           */
	/*     sinon                       : hdébut = 12.00           */

	/* v2.0 -> Heure début du graphe = hre fin de profil de la veille */

	CMPHJOU_getAttribute(pTable_hjou, "PRODEB", &wHjou_prodeb);
	if (wHjou_prodeb < wHjou_profin)
	{
		init(wHreFin, ' ');

		iHreFin = wHjou_prodeb;

		hre1for(wHreFin,iHreFin);
		memcpy(JOU1.BARGRAPH + k, "[", 1);
		k++;
		memcpy(JOU1.BARGRAPH + k, wHreFin, findb(wHreFin));
		k+=findb(wHreFin);
		memcpy(JOU1.BARGRAPH + k, "]", 1);
		k++;

		/* Heure de fin et type de chaque plage (si profil trouvé) */
		memcpy(JOU1.BARGRAPH + k, "[", 1);
		k++;

		for (i=0;i<18;i++)
		{
			if (PROF.Z [i].TYPPLA == ' ')
				break;

			hre1for(wHreFin,PROF.Z [i].HREFIN);
			memcpy(JOU1.BARGRAPH + k, wHreFin, findb(wHreFin));
			k+=findb(wHreFin);
			memcpy(JOU1.BARGRAPH + k, ";", 1);
			k++;
			if (PROF.Z [i].TYPPLA == GetPG()->TYPPLA [0])
				*(JOU1.BARGRAPH + k) = 'I';
			else if (PROF.Z [i].TYPPLA == GetPG()->TYPPLA [2])
				*(JOU1.BARGRAPH + k) = 'A';
			else if (PROF.Z [i].TYPPLA == GetPG()->TYPPLA [4])
			{
				if (iModProf == 1)
					*(JOU1.BARGRAPH + k) = 'P';
				else
					*(JOU1.BARGRAPH + k) = 'O';
			}
			k++;
			memcpy(JOU1.BARGRAPH + k, ";", 1);
			k++;
		}

		memcpy(JOU1.BARGRAPH + k, "]", 1);
	}
	else
		init(JOU1.BARGRAPH, ' ');

	/* Lecture du code horaire */
	if (T_TmemIgnoreSite(T_HORC, &HORA, sizeof(HORA), "HORCODE", JOU1.HORCODE ,sizeof(JOU1.HORCODE)) >= 0)
	{
		CMNLANG_Traduire_Libelle(HORA.HORCODE, sizeof(HORA.HORCODE), HORA.LIBCOURT, sizeof(HORA.LIBCOURT), "hophorc", "LIBCOURT", -1);
		move(JOU1.LIBHORCODE, HORA.LIBCOURT);
	}

	init(JOU1.LIBATTR, ' ');
	/* Lecture du code attribution */
	if (T_TmemIgnoreSite(T_ATTE, &ATTR, sizeof(ATTR), "ATTRIB", JOU1.ATTRIB,sizeof(JOU1.ATTRIB)) >= 0)
	{
		CMNLANG_Traduire_Libelle(ATTR.ATTRIB, sizeof(ATTR.ATTRIB), ATTR.LIBCOURT, sizeof(ATTR.LIBCOURT), "hopatte", "LIBCOURT", -1);
		move(JOU1.LIBATTR, ATTR.LIBCOURT);
	}

	heu1for(w_pretheo,PROF.PRETHEO);
	num1for(JOU1.PRETHEO,2,num2_HV,w_pretheo);

	PERaff1(NULL,JOU1.ZHJOU,EMPL.ZEMPL,"EMPL");
    PERaff1(NULL,JOU1.ZHJOU,CMPHJOU_getPerso(pTable_hjou), "HJOU");

	init(Work.wInMod, ' ');
    if ((depla = GetInMod()) != -1)
		CMPHJOU_getAttribute(pTable_hjou, "INMOD", &Work.wInMod);

	/* Lecture du mémo */
	HRRJOU_LectureMemo();

	/* Analytique */
	HRRJOU_LectureAnalytique(wHjou_date);

	/* Lecture des absences */
	HRRJOU_LectureAbsencesConsignes();

	/* Lecture des pointages */
	nbp = HRRJOU_LecturePointages((char *)pTable_hjou);

	j = 0;
	k = 0;
	/* Ajout des pointages dans tableau et sur graphe */
	if (findb(JOU1.BARGRAPH) > 0
	&&  nbp > 0)
	{
		k = findb(JOU1.BARGRAPH);
		memcpy(JOU1.BARGRAPH + k, "[", 1);
		k++;
	}

	for (i=0;i<MAXLIG_MVT;i++)
	{
		if (j >= nbp)
			break;

		/* Insertion des badgeages dans bargraph */
		if (findb(JOU1.BARGRAPH) > 0)
		{
			if (comp(JOU1.Z2 [i].HRETRAI, " ") != 0)
			{
				memcpy(JOU1.BARGRAPH + k, JOU1.Z2 [i].HRETRAI, findb(JOU1.Z2 [i].HRETRAI));
				k+=findb(JOU1.Z2 [i].HRETRAI);
				memcpy(JOU1.BARGRAPH + k, ";", 1);
				k++;
			}
		}

		++j;
	}

	if (findb(JOU1.BARGRAPH) > 0
	&&  nbp > 0)
		memcpy(JOU1.BARGRAPH + k, "]", 1);

	memcpy(Work.wecr, &JOU1, sizeof(JOU1));
	memcpy(Work.wecr + sizeof(JOU1), &poin, sizeof(poin));
	Work.nbp = nbp;

	move(GetCL()->MATRI,JOU1.MATRI);
	if (DATECTL(GetCL()->DATE,JOU1.DATE) < 0)
		init(GetCL()->DATE,' ');

	GenXmlParamEc();

	/* Lecture INMISS et déprotection colonne mission */
	if (VerifDico1(T_DICO,&DICO,"INMISS","HJOU",&depla) != -1)
	{
		CMPHJOU_getAttribute(pTable_hjou, "INMISS", &Work.wInmiss);
		if (Work.wInmiss == '1')
			GenXmlAuthDesc("MVT/MOTIF", PROTECT_N, VISIBLE_Y);
		else
			GenXmlAuthDesc("MVT/MOTIF", PROTECT_Y, VISIBLE_Y);
	}
	else
	{
		GenXmlAuthDesc("MVT/MOTIF", PROTECT_Y, VISIBLE_Y);
	}

Fin:

    if (pTable_hjou != NULL)
	   CMPHJOU_close(pTable_hjou);
	DB_Close_Table(T_EMPL);
	DB_Close_Table(T_SECH);
	DB_Close_Table(T_PROE);
	DB_Close_Table(T_ATTE);
	DB_Close_Table(T_HORC);
	DB_Close_Table(T_DICO);

	return retVal;
}

int HRRJOU_LecturePointages(char *pTable_hjou)
{
char wMess [S_MESS_LIB_SIZE*COEF_UNICODE+1];
char wTitreAnom [S_TEXT_TITRE_SIZE*COEF_UNICODE];
short int iGravNeuf, nbGPS = 0;
int nbp, nbano;
char wNbAno[2];

    HRSJOU_LecturePointages(pTable_hjou, "GRP_32", (char *)&JOU1.Z, (char *)&JOU1.Z2, &nbp, &nbano, &iGravNeuf, &nbGPS);
    if (memcmp(getReq()->id, getInfoProduit()->ID_EWOX, strlen(getInfoProduit()->ID_EWOX)) == 0)
        GenXmlAuthDesc("BTN_MAP", nbGPS > 0 ? PROTECT_N : PROTECT_Y, UNCHANGED);
    else
        GenXmlAuthDesc("BTN_MAP", nbGPS > 0 ? PROTECT_N : PROTECT_Y, VISIBLE_N);

	init(wTitreAnom ,' ');
	/* Gestion du warning [w]*/
	if (iGravNeuf == 1)
	{
		memcpy(wTitreAnom, "[w]", 3);
		memcpy(wTitreAnom + 3, Work.wTitreAnom, findb(Work.wTitreAnom));
		GenXmlPosPage("GRP_3",2);
	}
	else
		move(wTitreAnom,Work.wTitreAnom);

	if (nbano > 0)
	{
		init(wNbAno, ' ');
		INTCHAR(wNbAno, nbano, 2);
		memcpy(wTitreAnom + findb(wTitreAnom), " (", 2);
		memcpy(wTitreAnom + findb(wTitreAnom), wNbAno, 2);
		memcpy(wTitreAnom + findb(wTitreAnom), ")", 1);
	}

	GenXmlDyndesc("GRP_3/PAGE2",11, wTitreAnom,(short int)findb(wTitreAnom),NULL, 0);

	CMNMESS_LireMessage(289, "CM/SRV", wMess, sizeof(wMess));
	*(wMess + findb(wMess) + 1) = '\0';
	sprintf(wMess, wMess, nbp);
	GenXmlDyndesc("GRP_50", 6, wMess, (short int)findb(wMess), NULL, 0);

	return nbp;
}

void HRRJOU_InitElements(void)
{
char tpsNeg[5];

	if (Work.wTpsNeg == ' ')
	{
		CMNMESS_LireMessage(59, "HR/ANO", tpsNeg, sizeof(tpsNeg));
		if (memcmp(tpsNeg, "(+)", 3) == 0)
			Work.wTpsNeg = '0'; /*Temps négatif non géré*/
		else
			Work.wTpsNeg = '1'; /*Temps négatif géré*/
	}

	if (comp(Work.non_trouve, " ") == 0)
		CMNMESS_LireMessage(38, "CM/SRV", Work.non_trouve, sizeof(Work.non_trouve));

	/* Lecture du titre de la section horaire */
	if (comp(Work.wTitreSech, " ") == 0)
		CMNTEXT_LireLabItem( getReq()->fct, "GRP_15", 6, Work.wTitreSech, sizeof(Work.wTitreSech));

	/* Lecture du titre de l'onglet anomalie */
	if (comp(Work.wTitreAnom, " ") == 0)
		CMNTEXT_LireLabItem( getReq()->fct, "GRP_3/PAGE2", 11, Work.wTitreAnom, sizeof(Work.wTitreAnom));
}

int HRRJOU_ActionSET(void)
{
struct _TCB *T_PROE;
struct S_PROF PROF;
struct S_PROE PROE;
struct S_JOU1 wh1;
struct {
	short int HREDEB;
	short int HREFIN;
	short int DUREE;
	short int IND;
} W_ANA [MAX_ACTI];

struct S_HABS HABS2[MAX_ABSENCE];
struct S_HABS HABS3[MAX_ABSENCE];
short int w_rang;

short int W_HRETRAI [MAX_POINTAGE];
int AbsChanged = 0;
int CnsChanged = 0;
int CdcChanged = 0;
int FirstMvtInsert;
int maxMotif;
char StmtWhere[150];

int retVal = 0;
char *MsgG=NULL;
char wHjou_date[LG_DATE_INT];
char wHjou_Perso[CAPACITE_PERSO_HJOU];
short int wHjou_prodeb=0, wHjou_profin=0;
char wHjou_profil[LG_PROFIL];
char wHjou_attrib[LG_ATTRIB];
char wHjou_jour;
char wHjou_recalc;
hqt_handle pTable_hjoun;
hqt_handle pTable_hjoup;
char PARAMXCG[1];
int retourXCG=0;

	init(wh1,' ');
	memcpy(&wh1, Work.wecr, sizeof(wh1));

	/* Contrôle des champs */
	if (comp(JOU1.MATRI," ") == 0)
		GenXmlMsg(11, "EMP", "MATRI", " ", MsgErr);

	if (DATECTL(wHjou_date,JOU1.DATE) < 0)
		GenXmlMsg(22, "SRV", "DATE", " ", MsgErr);
	else
		CTRL_DatLim(wHjou_date,MAJ_GEN, "DATE");

	if (getNbError() != 0)
		return -1;

    pTable_hjoun = DAO_init(TABLE_HOPHJOUN,"h");
    pTable_hjoup = DAO_init(TABLE_HOPHJOUP,"hp");
	DAO_Setkey(1, JOU1.MATRI, LG_MATRI, SQLTYPE_CHR);
	DAO_Setkey(2, wHjou_date, LG_DATE_INT,SQLTYPE_DAT);
    DAO_prepareExecute(pTable_hjoun, DAO_SELECT, "h.*", "h.MATRI=:1 and h.DAT=$FmtD:2", NULL);
    if (DAO_fetch(pTable_hjoun, Normal) == 0)
	{
		DAO_getAttribute(pTable_hjoun, "JOUR", &wHjou_jour);
		DAO_getAttribute(pTable_hjoun, "PRODEB", &wHjou_prodeb);
		DAO_getAttribute(pTable_hjoun, "PROFIN", &wHjou_profin);

        DAO_prepareExecute(pTable_hjoup, DAO_SELECT, "hp.*", "hp.MATRI=:1 and hp.DAT=$FmtD:2", NULL);
        if (DAO_fetch(pTable_hjoup, Normal) == 0)
    		memcpy(wHjou_Perso, DAO_getPerso(pTable_hjoup), CAPACITE_PERSO_HJOU);
	}
	else
	{
		GenXmlMsg(15, "JOU", "DATE", JOU1.DATE, MsgErr);
		DAO_close(pTable_hjoun);
		DAO_close(pTable_hjoup);
		return -1;
	}

	/* Contrôle Changement de l'horaire */
	if (HRRJOU_ControleHoraire(wHjou_jour) != 0)
		retVal = -1;

	init(HABS2, ' ');
	init(HABS3, ' ');

	maxMotif = 0;
	maxMotif = HRSJOU_ControleAbsences(1, "ABS", JOU1.MATRI, wHjou_date, (char *)&JOU1.Z3, (char *)&wh1.Z3, HABS2, &AbsChanged);
	maxMotif += HRSJOU_ControleAbsences(2, "CNS", JOU1.MATRI, wHjou_date, (char *)&JOU1.Z4, (char *)&wh1.Z4, HABS3, &CnsChanged);

	/* Le nombre de motifs saisis est supérieur à celui autorisé -> erreur */
	if (maxMotif > MAX_ABSENCE)
	{
		GenXmlMsg(29, "JOU", NULL, " ", MsgErr);
		retVal = -1;
	}

	if ((CdcChanged = HRRJOU_ControleAnalytique((char *)&W_ANA, JOU1.MATRI, wHjou_date, wHjou_prodeb, wHjou_profin)) < 0)
		retVal = -1;

	if (HRSJOU_ControlePointages("MVT", (char *)&JOU1.Z2, (char *)&wh1.Z2, W_HRETRAI, JOU1.MATRI, wHjou_date, wHjou_prodeb, wHjou_profin, Work.wInmiss) < 0)
		retVal = -1;

	PERctrl1(NULL,JOU1.ZHJOU,wHjou_Perso,"HJOU");

	if (getNbError() != 0)
		retVal = -1;

	if (retVal == 0)
	{
		DAO_Setkey(1, JOU1.MATRI, LG_MATRI, SQLTYPE_CHR);
		DAO_Setkey(2, wHjou_date, LG_DATE_INT,SQLTYPE_DAT);
		DAO_prepareExecute(pTable_hjoun, DAO_SELECT, "h.*", "h.MATRI=:1 and h.DAT=$FmtD:2 for update", NULL);
		if (DAO_fetch(pTable_hjoun, PourUpdate) != 0)
		{
			GenXmlMsg(56, "JOU", "DATE", " ", MsgErr);
			retVal = -1;
		}
		else
		{
			DAO_getAttribute(pTable_hjoun, "PRODEB", &wHjou_prodeb);
			DAO_getAttribute(pTable_hjoun, "PROFIN", &wHjou_profin);
			DAO_getAttribute(pTable_hjoun, "PROFIL", wHjou_profil);
			DAO_getAttribute(pTable_hjoun, "ATTRIB", wHjou_attrib);
		}

		DAO_prepareExecute(pTable_hjoup, DAO_SELECT, "hp.*", "hp.MATRI=:1 and hp.DAT=$FmtD:2 for update", NULL);
		if (DAO_fetch(pTable_hjoup, PourUpdate) != 0)
		{
			GenXmlMsg(56, "JOU", "DATE", " ", MsgErr);
			retVal = -1;
		}
	}

	if (retVal == 0)
	{
		if (comp(JOU1.PROFIL, wHjou_profil) != 0)
		{
			if (comp(wHjou_profil, PROF.PROFIL) != 0)
			{
				T_PROE =DB_Open("hopproe", sizeof(PROE));

				if (T_Tmem(T_PROE, &PROF, sizeof(PROF), "PROFIL", JOU1.PROFIL, sizeof(JOU1.PROFIL)) >= 0)
					DAO_setAttribute(pTable_hjoun, "PROFIN", &PROF.PROFIN);

				DB_Close_Table(T_PROE);
			}
		}

		DAO_setAttribute(pTable_hjoun, "PROFIL", JOU1.PROFIL);
		DAO_setAttribute(pTable_hjoun, "HORCODE", JOU1.HORCODE);
		num1ctl(JOU1.RANG,0,num1_HV,w_rang);
		DAO_setAttribute(pTable_hjoun, "RANG", &w_rang);
		if (comp(JOU1.ATTRIB,wHjou_attrib) != 0)
		{
			wHjou_recalc = '0';
			DAO_setAttribute(pTable_hjoun, "RECALC", &wHjou_recalc);
		}
		DAO_setAttribute(pTable_hjoun, "ATTRIB", JOU1.ATTRIB);
		DAO_update(pTable_hjoun);

		/* Mise à jour du mémo */
		HRSJOU_UpdateMemo(JOU1.MATRI, wHjou_date, JOU1.MEMO, sizeof(JOU1.MEMO));

		HRSJOU_UpdateAbsence(1, JOU1.MATRI, wHjou_date, HABS2, AbsChanged);
		HRSJOU_UpdateAbsence(2, JOU1.MATRI, wHjou_date, HABS3, CnsChanged);
		HRRJOU_UpdateAnalytique((char *)&W_ANA, pTable_hjoun, CdcChanged);

		/* Mise à jour des pointages */
		FirstMvtInsert = HRSJOU_UpdatePointages(JOU1.MATRI, wHjou_date, wHjou_prodeb, wHjou_profin, (char *)&JOU1.Z2, (char *)&wh1.Z2, W_HRETRAI);
		/*NE PAS MODIFIER StmtWhere car appel à KER_PL_UpdatePosteProfil*/
		if(AbsChanged == 1){ /* BT32559 : uniquement en cas d'absence ou motif de présence */
			strcpy(StmtWhere,"where MATRI=:1 and DAT=$FmtD:2 for update");
			KER_PL_UpdatePosteProfil(JOU1.MATRI,wHjou_date,NULL,120,StmtWhere);
		}

		memcpy(DAO_getPerso(pTable_hjoup), wHjou_Perso, CAPACITE_PERSO_HJOU);
		DAO_update(pTable_hjoup);

		if (FirstMvtInsert == 1)
			wHjou_recalc = '0';
		else
			wHjou_recalc = '1';
		CMN_AppelCalcul(JOU1.MATRI, wHjou_date, NULL, wHjou_recalc, NULL,'1', GetGL()->DATJOU);

		if(AbsChanged == 1){
			PARAMXCG[0]=' ';
			retourXCG=CMN_CM_LectPLPCONF("VERIFCTRHI",10,NULL,PARAMXCG,1);
			if(PARAMXCG[0]=='1'||retourXCG==0) {
				if(maxMotif>0)
					KER_Test_Contrainte_PrecSuiv(JOU1.MATRI,wHjou_date,wHjou_date,&MsgG,0,NULL);
				else
					KER_Test_Contrainte_PrecSuiv(JOU1.MATRI,wHjou_date,wHjou_date,&MsgG,1,NULL);
			}
		}


	}

	if (MsgG!=NULL){
		GenXmlMsg(999, "SRV", " ", MsgG, MsgInfo);
		free(MsgG); MsgG=NULL;
	}
	DAO_close(pTable_hjoun);
	DAO_close(pTable_hjoup);
	return retVal;
}