/** @file RNAfold_cmdl.h
 *  @brief The header file for the command line option parser
 *  generated by GNU Gengetopt version 2.22.5
 *  http://www.gnu.org/software/gengetopt.
 *  DO NOT modify this file, since it can be overwritten
 *  @author GNU Gengetopt by Lorenzo Bettini */

#ifndef RNAFOLD_CMDL_H
#define RNAFOLD_CMDL_H

/* If we use autoconf.  */
#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include <stdio.h> /* for FILE */

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

#ifndef RNAFOLD_CMDLINE_PARSER_PACKAGE
/** @brief the program name (used for printing errors) */
#define RNAFOLD_CMDLINE_PARSER_PACKAGE "RNAfold"
#endif

#ifndef RNAFOLD_CMDLINE_PARSER_PACKAGE_NAME
/** @brief the complete program name (used for help and version) */
#define RNAFOLD_CMDLINE_PARSER_PACKAGE_NAME "RNAfold"
#endif

#ifndef RNAFOLD_CMDLINE_PARSER_VERSION
/** @brief the program version */
#define RNAFOLD_CMDLINE_PARSER_VERSION VERSION
#endif

/** @brief Where the command line options are stored */
struct RNAfold_args_info
{
  const char *help_help; /**< @brief Print help and exit help description.  */
  const char *detailed_help_help; /**< @brief Print help, including all details and hidden options, and exit help description.  */
  const char *full_help_help; /**< @brief Print help, including hidden options, and exit help description.  */
  const char *version_help; /**< @brief Print version and exit help description.  */
  int constraint_flag;	/**< @brief Calculate structures subject to constraints.
 (default=off).  */
  const char *constraint_help; /**< @brief Calculate structures subject to constraints.
 help description.  */
  int noconv_flag;	/**< @brief Do not automatically substitude nucleotide \"T\" with \"U\"
  
 (default=off).  */
  const char *noconv_help; /**< @brief Do not automatically substitude nucleotide \"T\" with \"U\"
  
 help description.  */
  int noPS_flag;	/**< @brief Do not produce postscript drawing of the mfe structure.
  
 (default=off).  */
  const char *noPS_help; /**< @brief Do not produce postscript drawing of the mfe structure.
  
 help description.  */
  int partfunc_arg;	/**< @brief Calculate the partition function and base pairing probability matrix.
 (default='1').  */
  char * partfunc_orig;	/**< @brief Calculate the partition function and base pairing probability matrix.
 original value given at command line.  */
  const char *partfunc_help; /**< @brief Calculate the partition function and base pairing probability matrix.
 help description.  */
  float MEA_arg;	/**< @brief Calculate an MEA (maximum expected accuracy) structure, where the expected accuracy is computed from the pair probabilities: each base pair (i,j) gets a score 2*gamma*p_ij and the score of an unpaired base is given by the probability of not forming a pair.
 (default='1.').  */
  char * MEA_orig;	/**< @brief Calculate an MEA (maximum expected accuracy) structure, where the expected accuracy is computed from the pair probabilities: each base pair (i,j) gets a score 2*gamma*p_ij and the score of an unpaired base is given by the probability of not forming a pair.
 original value given at command line.  */
  const char *MEA_help; /**< @brief Calculate an MEA (maximum expected accuracy) structure, where the expected accuracy is computed from the pair probabilities: each base pair (i,j) gets a score 2*gamma*p_ij and the score of an unpaired base is given by the probability of not forming a pair.
 help description.  */
  double pfScale_arg;	/**< @brief In the calculation of the pf use scale*mfe as an estimate for the ensemble free energy (used to avoid overflows).
.  */
  char * pfScale_orig;	/**< @brief In the calculation of the pf use scale*mfe as an estimate for the ensemble free energy (used to avoid overflows).
 original value given at command line.  */
  const char *pfScale_help; /**< @brief In the calculation of the pf use scale*mfe as an estimate for the ensemble free energy (used to avoid overflows).
 help description.  */
  int circ_flag;	/**< @brief Assume a circular (instead of linear) RNA molecule.
 (default=off).  */
  const char *circ_help; /**< @brief Assume a circular (instead of linear) RNA molecule.
 help description.  */
  int ImFeelingLucky_flag;	/**< @brief Return exactly one stochastically backtracked structure
 (default=off).  */
  const char *ImFeelingLucky_help; /**< @brief Return exactly one stochastically backtracked structure
 help description.  */
  double bppmThreshold_arg;	/**< @brief Set the threshold for base pair probabilities included in the postscript output
 (default='1e-5').  */
  char * bppmThreshold_orig;	/**< @brief Set the threshold for base pair probabilities included in the postscript output
 original value given at command line.  */
  const char *bppmThreshold_help; /**< @brief Set the threshold for base pair probabilities included in the postscript output
 help description.  */
  int gquad_flag;	/**< @brief Incoorporate G-Quadruplex formation into the structure prediction algorithm
 (default=off).  */
  const char *gquad_help; /**< @brief Incoorporate G-Quadruplex formation into the structure prediction algorithm
 help description.  */
  double temp_arg;	/**< @brief Rescale energy parameters to a temperature of temp C. Default is 37C.
  
.  */
  char * temp_orig;	/**< @brief Rescale energy parameters to a temperature of temp C. Default is 37C.
  
 original value given at command line.  */
  const char *temp_help; /**< @brief Rescale energy parameters to a temperature of temp C. Default is 37C.
  
 help description.  */
  int noTetra_flag;	/**< @brief Do not include special tabulated stabilizing energies for tri-, tetra- and hexaloop hairpins. Mostly for testing.
  
 (default=off).  */
  const char *noTetra_help; /**< @brief Do not include special tabulated stabilizing energies for tri-, tetra- and hexaloop hairpins. Mostly for testing.
  
 help description.  */
  int dangles_arg;	/**< @brief How to treat \"dangling end\" energies for bases adjacent to helices in free ends and multi-loops
 (default='2').  */
  char * dangles_orig;	/**< @brief How to treat \"dangling end\" energies for bases adjacent to helices in free ends and multi-loops
 original value given at command line.  */
  const char *dangles_help; /**< @brief How to treat \"dangling end\" energies for bases adjacent to helices in free ends and multi-loops
 help description.  */
  int noLP_flag;	/**< @brief Produce structures without lonely pairs (helices of length 1).
 (default=off).  */
  const char *noLP_help; /**< @brief Produce structures without lonely pairs (helices of length 1).
 help description.  */
  int noGU_flag;	/**< @brief Do not allow GU pairs
  
 (default=off).  */
  const char *noGU_help; /**< @brief Do not allow GU pairs
  
 help description.  */
  int noClosingGU_flag;	/**< @brief Do not allow GU pairs at the end of helices
  
 (default=off).  */
  const char *noClosingGU_help; /**< @brief Do not allow GU pairs at the end of helices
  
 help description.  */
  char * paramFile_arg;	/**< @brief Read energy parameters from paramfile, instead of using the default parameter set.
.  */
  char * paramFile_orig;	/**< @brief Read energy parameters from paramfile, instead of using the default parameter set.
 original value given at command line.  */
  const char *paramFile_help; /**< @brief Read energy parameters from paramfile, instead of using the default parameter set.
 help description.  */
  char * nsp_arg;	/**< @brief Allow other pairs in addition to the usual AU,GC,and GU pairs.
.  */
  char * nsp_orig;	/**< @brief Allow other pairs in addition to the usual AU,GC,and GU pairs.
 original value given at command line.  */
  const char *nsp_help; /**< @brief Allow other pairs in addition to the usual AU,GC,and GU pairs.
 help description.  */
  int energyModel_arg;	/**< @brief Rarely used option to fold sequences from the artificial ABCD... alphabet, where A pairs B, C-D etc.  Use the energy parameters for GC (-e 1) or AU (-e 2) pairs.
  
.  */
  char * energyModel_orig;	/**< @brief Rarely used option to fold sequences from the artificial ABCD... alphabet, where A pairs B, C-D etc.  Use the energy parameters for GC (-e 1) or AU (-e 2) pairs.
  
 original value given at command line.  */
  const char *energyModel_help; /**< @brief Rarely used option to fold sequences from the artificial ABCD... alphabet, where A pairs B, C-D etc.  Use the energy parameters for GC (-e 1) or AU (-e 2) pairs.
  
 help description.  */
  double betaScale_arg;	/**< @brief Set the scaling of the Boltzmann factors
 (default='1.').  */
  char * betaScale_orig;	/**< @brief Set the scaling of the Boltzmann factors
 original value given at command line.  */
  const char *betaScale_help; /**< @brief Set the scaling of the Boltzmann factors
 help description.  */
  
  unsigned int help_given ;	/**< @brief Whether help was given.  */
  unsigned int detailed_help_given ;	/**< @brief Whether detailed-help was given.  */
  unsigned int full_help_given ;	/**< @brief Whether full-help was given.  */
  unsigned int version_given ;	/**< @brief Whether version was given.  */
  unsigned int constraint_given ;	/**< @brief Whether constraint was given.  */
  unsigned int noconv_given ;	/**< @brief Whether noconv was given.  */
  unsigned int noPS_given ;	/**< @brief Whether noPS was given.  */
  unsigned int partfunc_given ;	/**< @brief Whether partfunc was given.  */
  unsigned int MEA_given ;	/**< @brief Whether MEA was given.  */
  unsigned int pfScale_given ;	/**< @brief Whether pfScale was given.  */
  unsigned int circ_given ;	/**< @brief Whether circ was given.  */
  unsigned int ImFeelingLucky_given ;	/**< @brief Whether ImFeelingLucky was given.  */
  unsigned int bppmThreshold_given ;	/**< @brief Whether bppmThreshold was given.  */
  unsigned int gquad_given ;	/**< @brief Whether gquad was given.  */
  unsigned int temp_given ;	/**< @brief Whether temp was given.  */
  unsigned int noTetra_given ;	/**< @brief Whether noTetra was given.  */
  unsigned int dangles_given ;	/**< @brief Whether dangles was given.  */
  unsigned int noLP_given ;	/**< @brief Whether noLP was given.  */
  unsigned int noGU_given ;	/**< @brief Whether noGU was given.  */
  unsigned int noClosingGU_given ;	/**< @brief Whether noClosingGU was given.  */
  unsigned int paramFile_given ;	/**< @brief Whether paramFile was given.  */
  unsigned int nsp_given ;	/**< @brief Whether nsp was given.  */
  unsigned int energyModel_given ;	/**< @brief Whether energyModel was given.  */
  unsigned int betaScale_given ;	/**< @brief Whether betaScale was given.  */

} ;

/** @brief The additional parameters to pass to parser functions */
struct RNAfold_cmdline_parser_params
{
  int override; /**< @brief whether to override possibly already present options (default 0) */
  int initialize; /**< @brief whether to initialize the option structure RNAfold_args_info (default 1) */
  int check_required; /**< @brief whether to check that all required options were provided (default 1) */
  int check_ambiguity; /**< @brief whether to check for options already specified in the option structure RNAfold_args_info (default 0) */
  int print_errors; /**< @brief whether getopt_long should print an error message for a bad option (default 1) */
} ;

/** @brief the purpose string of the program */
extern const char *RNAfold_args_info_purpose;
/** @brief the usage string of the program */
extern const char *RNAfold_args_info_usage;
/** @brief all the lines making the help output */
extern const char *RNAfold_args_info_help[];
/** @brief all the lines making the full help output (including hidden options) */
extern const char *RNAfold_args_info_full_help[];
/** @brief all the lines making the detailed help output (including hidden options and details) */
extern const char *RNAfold_args_info_detailed_help[];

/**
 * The command line parser
 * @param argc the number of command line options
 * @param argv the command line options
 * @param args_info the structure where option information will be stored
 * @return 0 if everything went fine, NON 0 if an error took place
 */
int RNAfold_cmdline_parser (int argc, char **argv,
  struct RNAfold_args_info *args_info);

/**
 * The command line parser (version with additional parameters - deprecated)
 * @param argc the number of command line options
 * @param argv the command line options
 * @param args_info the structure where option information will be stored
 * @param override whether to override possibly already present options
 * @param initialize whether to initialize the option structure my_args_info
 * @param check_required whether to check that all required options were provided
 * @return 0 if everything went fine, NON 0 if an error took place
 * @deprecated use RNAfold_cmdline_parser_ext() instead
 */
int RNAfold_cmdline_parser2 (int argc, char **argv,
  struct RNAfold_args_info *args_info,
  int override, int initialize, int check_required);

/**
 * The command line parser (version with additional parameters)
 * @param argc the number of command line options
 * @param argv the command line options
 * @param args_info the structure where option information will be stored
 * @param params additional parameters for the parser
 * @return 0 if everything went fine, NON 0 if an error took place
 */
int RNAfold_cmdline_parser_ext (int argc, char **argv,
  struct RNAfold_args_info *args_info,
  struct RNAfold_cmdline_parser_params *params);

/**
 * Save the contents of the option struct into an already open FILE stream.
 * @param outfile the stream where to dump options
 * @param args_info the option struct to dump
 * @return 0 if everything went fine, NON 0 if an error took place
 */
int RNAfold_cmdline_parser_dump(FILE *outfile,
  struct RNAfold_args_info *args_info);

/**
 * Save the contents of the option struct into a (text) file.
 * This file can be read by the config file parser (if generated by gengetopt)
 * @param filename the file where to save
 * @param args_info the option struct to save
 * @return 0 if everything went fine, NON 0 if an error took place
 */
int RNAfold_cmdline_parser_file_save(const char *filename,
  struct RNAfold_args_info *args_info);

/**
 * Print the help
 */
void RNAfold_cmdline_parser_print_help(void);
/**
 * Print the full help (including hidden options)
 */
void RNAfold_cmdline_parser_print_full_help(void);
/**
 * Print the detailed help (including hidden options and details)
 */
void RNAfold_cmdline_parser_print_detailed_help(void);
/**
 * Print the version
 */
void RNAfold_cmdline_parser_print_version(void);

/**
 * Initializes all the fields a RNAfold_cmdline_parser_params structure 
 * to their default values
 * @param params the structure to initialize
 */
void RNAfold_cmdline_parser_params_init(struct RNAfold_cmdline_parser_params *params);

/**
 * Allocates dynamically a RNAfold_cmdline_parser_params structure and initializes
 * all its fields to their default values
 * @return the created and initialized RNAfold_cmdline_parser_params structure
 */
struct RNAfold_cmdline_parser_params *RNAfold_cmdline_parser_params_create(void);

/**
 * Initializes the passed RNAfold_args_info structure's fields
 * (also set default values for options that have a default)
 * @param args_info the structure to initialize
 */
void RNAfold_cmdline_parser_init (struct RNAfold_args_info *args_info);
/**
 * Deallocates the string fields of the RNAfold_args_info structure
 * (but does not deallocate the structure itself)
 * @param args_info the structure to deallocate
 */
void RNAfold_cmdline_parser_free (struct RNAfold_args_info *args_info);

/**
 * Checks that all the required options were specified
 * @param args_info the structure to check
 * @param prog_name the name of the program that will be used to print
 *   possible errors
 * @return
 */
int RNAfold_cmdline_parser_required (struct RNAfold_args_info *args_info,
  const char *prog_name);


#ifdef __cplusplus
}
#endif /* __cplusplus */
#endif /* RNAFOLD_CMDL_H */
