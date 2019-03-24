package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.ProdutoService;
import br.com.acaipaidegua.domain.Produto;
import br.com.acaipaidegua.repository.ProdutoRepository;
import br.com.acaipaidegua.service.dto.ProdutoDTO;
import br.com.acaipaidegua.service.mapper.ProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Produto.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoMapper produtoMapper;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    /**
     * Save a produto.
     *
     * @param produtoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        log.debug("Request to save Produto : {}", produtoDTO);
        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDto(produto);
    }

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable)
            .map(produtoMapper::toDto);
    }


    /**
     * Get one produto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProdutoDTO> findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findById(id)
            .map(produtoMapper::toDto);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
    }
}
